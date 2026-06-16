package com.nh.nsight.common.updownload.service;

import com.nh.nsight.common.updownload.model.UploadFileMeta;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UpdownloadService {
    private static final DateTimeFormatter TS = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private final JdbcTemplate jdbcTemplate;
    private final FileStorageService fileStorageService;

    public UpdownloadService(JdbcTemplate jdbcTemplate, FileStorageService fileStorageService) {
        this.jdbcTemplate = jdbcTemplate;
        this.fileStorageService = fileStorageService;
    }

    public Map<String, Object> uploadFromBase64(String fileName, String contentType, String contentBase64,
                                                String userId, String description) {
        if (!StringUtils.hasText(fileName)) {
            throw new IllegalArgumentException("fileName은 필수입니다.");
        }
        byte[] content = decodeBase64(contentBase64);
        if (content.length == 0) {
            throw new IllegalArgumentException("업로드 파일이 비어 있습니다.");
        }
        try {
            UploadFileMeta meta = fileStorageService.store(
                    fileName,
                    StringUtils.hasText(contentType) ? contentType : "application/octet-stream",
                    content
            );
            String uploadTime = LocalDateTime.now().format(TS);
            jdbcTemplate.update(
                    "INSERT INTO UD_FILE (FILE_ID, ORIGINAL_NAME, STORED_PATH, CONTENT_TYPE, FILE_SIZE, UPLOAD_USER, UPLOAD_TIME, BUSINESS_CODE, DESCRIPTION) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    meta.getFileId(),
                    meta.getOriginalFilename(),
                    meta.getStoredPath(),
                    meta.getContentType(),
                    meta.getSize(),
                    StringUtils.hasText(userId) ? userId : "U123456",
                    uploadTime,
                    "UD",
                    description
            );
            return detail(meta.getFileId());
        } catch (IOException e) {
            throw new IllegalStateException("파일 저장 중 오류가 발생했습니다.", e);
        }
    }

    public Map<String, Object> upload(MultipartFile file, String userId, String description) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("업로드 파일이 비어 있습니다.");
        }
        try {
            UploadFileMeta meta = fileStorageService.store(file);
            String uploadTime = LocalDateTime.now().format(TS);
            jdbcTemplate.update(
                    "INSERT INTO UD_FILE (FILE_ID, ORIGINAL_NAME, STORED_PATH, CONTENT_TYPE, FILE_SIZE, UPLOAD_USER, UPLOAD_TIME, BUSINESS_CODE, DESCRIPTION) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    meta.getFileId(),
                    meta.getOriginalFilename(),
                    meta.getStoredPath(),
                    meta.getContentType(),
                    meta.getSize(),
                    StringUtils.hasText(userId) ? userId : "U123456",
                    uploadTime,
                    "UD",
                    description
            );
            return detail(meta.getFileId());
        } catch (IOException e) {
            throw new IllegalStateException("파일 저장 중 오류가 발생했습니다.", e);
        }
    }

    public Map<String, Object> detail(String fileId) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                "SELECT FILE_ID, ORIGINAL_NAME, CONTENT_TYPE, FILE_SIZE, UPLOAD_USER, UPLOAD_TIME, BUSINESS_CODE, DESCRIPTION FROM UD_FILE WHERE FILE_ID = ?",
                fileId
        );
        if (rows.isEmpty()) {
            throw new IllegalArgumentException("파일을 찾을 수 없습니다: " + fileId);
        }
        return toBody(rows.get(0));
    }

    public Map<String, Object> list(String originalName, String uploadUser, String fromDate, String toDate, int pageNo, int pageSize) {
        int safePageNo = Math.max(1, pageNo);
        int safePageSize = Math.min(100, Math.max(1, pageSize));
        int offset = (safePageNo - 1) * safePageSize;

        StringBuilder where = new StringBuilder(" WHERE BUSINESS_CODE = 'UD' ");
        java.util.ArrayList<Object> params = new java.util.ArrayList<>();
        if (StringUtils.hasText(originalName)) {
            where.append(" AND ORIGINAL_NAME LIKE ? ");
            params.add("%" + originalName.trim() + "%");
        }
        if (StringUtils.hasText(uploadUser)) {
            where.append(" AND UPLOAD_USER = ? ");
            params.add(uploadUser.trim());
        }
        if (StringUtils.hasText(fromDate)) {
            where.append(" AND UPLOAD_TIME >= ? ");
            params.add(normalizeFromDate(fromDate));
        }
        if (StringUtils.hasText(toDate)) {
            where.append(" AND UPLOAD_TIME <= ? ");
            params.add(normalizeToDate(toDate));
        }

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                "SELECT FILE_ID, ORIGINAL_NAME, CONTENT_TYPE, FILE_SIZE, UPLOAD_USER, UPLOAD_TIME, BUSINESS_CODE, DESCRIPTION FROM UD_FILE "
                        + where + " ORDER BY UPLOAD_TIME DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY",
                append(params, offset, safePageSize)
        );
        Integer total = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM UD_FILE " + where,
                Integer.class,
                params.toArray()
        );

        List<Map<String, Object>> files = rows.stream().map(this::toBody).toList();
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("files", files);
        body.put("totalCount", total == null ? 0 : total);
        body.put("pageNo", safePageNo);
        body.put("pageSize", safePageSize);
        return body;
    }

    public Map<String, Object> update(String fileId, String description) {
        int updated = jdbcTemplate.update(
                "UPDATE UD_FILE SET DESCRIPTION = ? WHERE FILE_ID = ?",
                description,
                fileId
        );
        if (updated == 0) {
            throw new IllegalArgumentException("파일을 찾을 수 없습니다: " + fileId);
        }
        return detail(fileId);
    }

    public Map<String, Object> delete(String fileId) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                "SELECT STORED_PATH FROM UD_FILE WHERE FILE_ID = ?",
                fileId
        );
        if (rows.isEmpty()) {
            throw new IllegalArgumentException("파일을 찾을 수 없습니다: " + fileId);
        }
        jdbcTemplate.update("DELETE FROM UD_FILE WHERE FILE_ID = ?", fileId);
        Object storedPath = rows.get(0).get("STORED_PATH");
        if (storedPath != null) {
            try {
                Files.deleteIfExists(Path.of(String.valueOf(storedPath)));
            } catch (IOException ignored) {
            }
        }
        return Map.of("fileId", fileId, "deleted", true);
    }

    public byte[] downloadBytes(String fileId) throws IOException {
        return download(fileId);
    }

    public Map<String, Object> downloadWithBase64(String fileId) throws IOException {
        Map<String, Object> file = detail(fileId);
        byte[] content = download(fileId);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("file", file);
        result.put("contentBase64", Base64.getEncoder().encodeToString(content));
        result.put("downloadUrl", "/ud/files/" + fileId + "/download");
        return result;
    }

    public byte[] download(String fileId) throws IOException {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                "SELECT STORED_PATH FROM UD_FILE WHERE FILE_ID = ?",
                fileId
        );
        if (rows.isEmpty()) {
            throw new IllegalArgumentException("파일을 찾을 수 없습니다: " + fileId);
        }
        return Files.readAllBytes(Path.of(String.valueOf(rows.get(0).get("STORED_PATH"))));
    }

    private byte[] decodeBase64(String contentBase64) {
        if (!StringUtils.hasText(contentBase64)) {
            return new byte[0];
        }
        return Base64.getDecoder().decode(contentBase64);
    }

    private Map<String, Object> toBody(Map<String, Object> row) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("fileId", row.get("FILE_ID"));
        body.put("originalName", row.get("ORIGINAL_NAME"));
        body.put("contentType", row.get("CONTENT_TYPE"));
        body.put("fileSize", row.get("FILE_SIZE"));
        body.put("uploadUser", row.get("UPLOAD_USER"));
        body.put("uploadTime", row.get("UPLOAD_TIME"));
        body.put("businessCode", row.get("BUSINESS_CODE"));
        body.put("description", row.get("DESCRIPTION"));
        return body;
    }

    private Object[] append(List<Object> source, Object... tail) {
        Object[] merged = new Object[source.size() + tail.length];
        for (int i = 0; i < source.size(); i++) {
            merged[i] = source.get(i);
        }
        System.arraycopy(tail, 0, merged, source.size(), tail.length);
        return merged;
    }

    private String normalizeFromDate(String value) {
        String normalized = value.replace("-", "").replace(":", "").replace("T", "").replace(" ", "");
        if (normalized.length() >= 14) {
            return normalized.substring(0, 14);
        }
        if (normalized.length() == 8) {
            return normalized + "000000";
        }
        return LocalDate.parse(value).format(DateTimeFormatter.BASIC_ISO_DATE) + "000000";
    }

    private String normalizeToDate(String value) {
        String normalized = value.replace("-", "").replace(":", "").replace("T", "").replace(" ", "");
        if (normalized.length() >= 14) {
            return normalized.substring(0, 14);
        }
        if (normalized.length() == 8) {
            return normalized + "235959";
        }
        return LocalDate.parse(value).format(DateTimeFormatter.BASIC_ISO_DATE) + "235959";
    }
}
