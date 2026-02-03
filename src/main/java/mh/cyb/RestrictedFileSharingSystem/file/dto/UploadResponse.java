package mh.cyb.RestrictedFileSharingSystem.file.dto;

import java.util.UUID;

public class UploadResponse {
    private UUID fileId;
    private String fileName;
    private String message;

    public UploadResponse(UUID fileId, String fileName, String message) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.message = message;
    }

    public UUID getFileId() {
        return fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getMessage() {
        return message;
    }
}
