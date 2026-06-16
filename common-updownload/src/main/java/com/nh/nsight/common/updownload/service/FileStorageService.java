package com.nh.nsight.common.updownload.service;

import com.nh.nsight.common.updownload.model.UploadFileMeta;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
    private final Path storageRoot;

    public FileStorageService(@Value("${nsight.file.storage-root:upload-data}") String storageRoot) {
        this.storageRoot = Path.of(storageRoot);
    }

    public UploadFileMeta store(MultipartFile file) throws IOException {
        return store(
                file.getOriginalFilename(),
                file.getContentType(),
                file.getInputStream(),
                file.getSize()
        );
    }

    public UploadFileMeta store(String originalFilename, String contentType, byte[] content) throws IOException {
        return store(originalFilename, contentType, new ByteArrayInputStream(content), content.length);
    }

    private UploadFileMeta store(String originalFilename, String contentType, InputStream in, long size) throws IOException {
        Files.createDirectories(storageRoot);
        String fileId = UUID.randomUUID().toString();
        Path target = storageRoot.resolve(fileId + ".bin");
        try (InputStream input = in) {
            Files.copy(input, target);
        }
        UploadFileMeta meta = new UploadFileMeta();
        meta.setFileId(fileId);
        meta.setOriginalFilename(originalFilename);
        meta.setStoredPath(target.toString());
        meta.setSize(size);
        meta.setContentType(contentType);
        return meta;
    }

    public Path load(String fileId) {
        return storageRoot.resolve(fileId + ".bin");
    }
}
