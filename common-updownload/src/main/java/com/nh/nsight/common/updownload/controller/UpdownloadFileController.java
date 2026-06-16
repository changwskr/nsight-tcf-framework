package com.nh.nsight.common.updownload.controller;

import com.nh.nsight.tcf.core.message.Result;
import com.nh.nsight.tcf.core.message.StandardResponse;
import com.nh.nsight.tcf.web.gateway.TcfGateway;
import com.nh.nsight.tcf.web.gateway.TcfGateway.TcfInvokeRequest;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/ud/files")
public class UpdownloadFileController {
    private final TcfGateway tcfGateway;

    public UpdownloadFileController(TcfGateway tcfGateway) {
        this.tcfGateway = tcfGateway;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public StandardResponse<Object> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "description", required = false) String description,
            HttpServletRequest servletRequest) throws Exception {
        String fileName = file.getOriginalFilename();
        if (!StringUtils.hasText(fileName)) {
            fileName = "unknown.bin";
        }
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("fileName", fileName);
        body.put("contentType", StringUtils.hasText(file.getContentType())
                ? file.getContentType() : MediaType.APPLICATION_OCTET_STREAM_VALUE);
        body.put("contentBase64", Base64.getEncoder().encodeToString(file.getBytes()));
        body.put("description", description);
        return invoke("UD.File.upload", "UD-UPL-0001", "CREATE", body, userId, servletRequest);
    }

    @GetMapping("/{fileId}")
    public StandardResponse<Object> detail(
            @PathVariable("fileId") String fileId,
            HttpServletRequest servletRequest) {
        return invoke("UD.File.detail", "UD-DTL-0001", "INQUIRY", Map.of("fileId", fileId), null, servletRequest);
    }

    @PutMapping("/{fileId}")
    public StandardResponse<Object> update(
            @PathVariable("fileId") String fileId,
            @RequestBody(required = false) Map<String, Object> body,
            HttpServletRequest servletRequest) {
        Map<String, Object> requestBody = new LinkedHashMap<>();
        requestBody.put("fileId", fileId);
        if (body != null) {
            requestBody.putAll(body);
        }
        return invoke("UD.File.update", "UD-UPD-0001", "UPDATE", requestBody, null, servletRequest);
    }

    @DeleteMapping("/{fileId}")
    public StandardResponse<Object> delete(
            @PathVariable("fileId") String fileId,
            HttpServletRequest servletRequest) {
        return invoke("UD.File.delete", "UD-DEL-0001", "DELETE", Map.of("fileId", fileId), null, servletRequest);
    }

    @GetMapping
    public StandardResponse<Object> list(
            @RequestParam(value = "originalName", required = false) String originalName,
            @RequestParam(value = "uploadUser", required = false) String uploadUser,
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate,
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            HttpServletRequest servletRequest) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("originalName", originalName);
        body.put("uploadUser", uploadUser);
        body.put("fromDate", fromDate);
        body.put("toDate", toDate);
        body.put("pageNo", pageNo);
        body.put("pageSize", pageSize);
        return invoke("UD.File.list", "UD-LST-0001", "INQUIRY", body, null, servletRequest);
    }

    @GetMapping("/{fileId}/download")
    public ResponseEntity<ByteArrayResource> download(
            @PathVariable("fileId") String fileId,
            HttpServletRequest servletRequest) {
        StandardResponse<Object> response = invoke(
                "UD.File.download", "UD-DWN-0001", "INQUIRY", Map.of("fileId", fileId), null, servletRequest
        );
        if (!isSuccess(response)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage(response));
        }
        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        @SuppressWarnings("unchecked")
        Map<String, Object> meta = body == null ? Map.of() : (Map<String, Object>) body.getOrDefault("file", Map.of());
        String contentBase64 = body == null ? null : String.valueOf(body.get("contentBase64"));
        if (!StringUtils.hasText(contentBase64) || "null".equals(contentBase64)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "파일 내용을 찾을 수 없습니다.");
        }
        byte[] content = Base64.getDecoder().decode(contentBase64);
        String originalName = String.valueOf(meta.getOrDefault("originalName", fileId + ".bin"));
        String contentType = String.valueOf(meta.getOrDefault("contentType", MediaType.APPLICATION_OCTET_STREAM_VALUE));
        String encodedName = URLEncoder.encode(originalName, StandardCharsets.UTF_8).replace("+", "%20");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedName)
                .contentType(MediaType.parseMediaType(contentType))
                .contentLength(content.length)
                .body(new ByteArrayResource(content));
    }

    private StandardResponse<Object> invoke(String serviceId, String transactionCode, String processingType,
                                            Map<String, Object> body, String userId, HttpServletRequest servletRequest) {
        TcfInvokeRequest invokeRequest = TcfInvokeRequest.builder(serviceId, transactionCode, processingType)
                .body(body)
                .userId(StringUtils.hasText(userId) ? userId : "U123456")
                .clientIp(resolveClientIp(servletRequest))
                .build();
        return tcfGateway.invoke(invokeRequest);
    }

    private boolean isSuccess(StandardResponse<Object> response) {
        Result result = response.getResult();
        return result != null && "S0000".equals(result.getResultCode());
    }

    private String errorMessage(StandardResponse<Object> response) {
        Result result = response.getResult();
        if (result == null) {
            return "처리 중 오류가 발생했습니다.";
        }
        if (StringUtils.hasText(result.getErrorMessage())) {
            return result.getErrorMessage();
        }
        return result.getResultMessage();
    }

    private String resolveClientIp(HttpServletRequest request) {
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (StringUtils.hasText(forwardedFor)) {
            return forwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
