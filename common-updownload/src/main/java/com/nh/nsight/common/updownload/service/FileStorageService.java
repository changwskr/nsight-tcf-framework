package com.nh.nsight.common.updownload.service;

import com.nh.nsight.common.updownload.model.UploadFileMeta;
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
        Files.createDirectories(storageRoot);
        String fileId = UUID.randomUUID().toString();
        Path target = storageRoot.resolve(fileId + ".bin");
        try (InputStream in = file.getInputStream()) {
            Files.copy(in, target);
        }
        UploadFileMeta meta = new UploadFileMeta();
        meta.setFileId(fileId);
        meta.setOriginalFilename(file.getOriginalFilename());
        meta.setStoredPath(target.toString());
        meta.setSize(file.getSize());
        meta.setContentType(file.getContentType());
        return meta;
    }

    public Path load(String fileId) {
        return storageRoot.resolve(fileId + ".bin");
    }
}
