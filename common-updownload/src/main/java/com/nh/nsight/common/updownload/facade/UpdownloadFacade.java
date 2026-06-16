package com.nh.nsight.common.updownload.facade;

import com.nh.nsight.common.updownload.service.UpdownloadService;
import com.nh.nsight.tcf.core.context.TransactionContext;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class UpdownloadFacade {
    private final UpdownloadService service;

    public UpdownloadFacade(UpdownloadService service) {
        this.service = service;
    }

    public Map<String, Object> upload(Map<String, Object> body, TransactionContext context) {
        String userId = context.getHeader() == null ? null : context.getHeader().getUserId();
        return wrapFile(service.uploadFromBase64(
                stringValue(body, "fileName"),
                stringValue(body, "contentType"),
                stringValue(body, "contentBase64"),
                userId,
                stringValue(body, "description")
        ), "file");
    }

    public Map<String, Object> download(Map<String, Object> body, TransactionContext context) {
        try {
            Map<String, Object> result = service.downloadWithBase64(required(body, "fileId"));
            Map<String, Object> wrapped = new LinkedHashMap<>();
            wrapped.put("action", "download");
            wrapped.putAll(result);
            return wrapped;
        } catch (IOException e) {
            throw new IllegalStateException("파일 다운로드 중 오류가 발생했습니다.", e);
        }
    }

    public Map<String, Object> list(Map<String, Object> body, TransactionContext context) {
        String originalName = stringValue(body, "originalName");
        String uploadUser = stringValue(body, "uploadUser");
        String fromDate = stringValue(body, "fromDate");
        String toDate = stringValue(body, "toDate");
        int pageNo = intValue(body, "pageNo", 1);
        int pageSize = intValue(body, "pageSize", 20);
        return service.list(originalName, uploadUser, fromDate, toDate, pageNo, pageSize);
    }

    public Map<String, Object> detail(Map<String, Object> body, TransactionContext context) {
        return wrapFile(service.detail(required(body, "fileId")));
    }

    public Map<String, Object> update(Map<String, Object> body, TransactionContext context) {
        String fileId = required(body, "fileId");
        String description = stringValue(body, "description");
        return wrapFile(service.update(fileId, description == null ? "" : description));
    }

    public Map<String, Object> delete(Map<String, Object> body, TransactionContext context) {
        String fileId = required(body, "fileId");
        Map<String, Object> file = service.detail(fileId);
        service.delete(fileId);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("deleted", true);
        result.put("file", file);
        return result;
    }

    private Map<String, Object> wrapFile(Map<String, Object> file, String action) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("action", action);
        result.put("file", file);
        result.put("downloadUrl", "/ud/files/" + file.get("fileId") + "/download");
        return result;
    }

    private Map<String, Object> wrapFile(Map<String, Object> file) {
        return wrapFile(file, "detail");
    }

    private String required(Map<String, Object> body, String key) {
        String value = stringValue(body, key);
        if (value == null) {
            throw new IllegalArgumentException(key + "은(는) 필수입니다.");
        }
        return value;
    }

    private String stringValue(Map<String, Object> body, String key) {
        if (body == null || body.get(key) == null) {
            return null;
        }
        String value = String.valueOf(body.get(key)).trim();
        return value.isEmpty() ? null : value;
    }

    private int intValue(Map<String, Object> body, String key, int defaultValue) {
        String value = stringValue(body, key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
