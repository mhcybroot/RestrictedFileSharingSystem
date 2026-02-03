package mh.cyb.RestrictedFileSharingSystem.file.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import mh.cyb.RestrictedFileSharingSystem.file.dto.ShareRequest;
import mh.cyb.RestrictedFileSharingSystem.file.dto.UploadResponse;
import mh.cyb.RestrictedFileSharingSystem.file.entity.FileMetadata;
import mh.cyb.RestrictedFileSharingSystem.file.service.FileSharingService;

import java.util.UUID;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileSharingService fileSharingService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<UploadResponse> uploadFile(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal UserDetails userDetails) {

        try {
            FileMetadata metadata = fileSharingService.uploadFile(file, userDetails.getUsername());
            return ResponseEntity.ok(new UploadResponse(
                    metadata.getId(),
                    metadata.getFileName(),
                    "File uploaded successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new UploadResponse(null, null, e.getMessage()));
        }
    }

    @PostMapping("/{id}/share")
    public ResponseEntity<String> shareFile(
            @PathVariable UUID id,
            @RequestBody @Valid ShareRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        try {
            fileSharingService.shareFile(id, userDetails.getUsername(), request.getEmail());
            return ResponseEntity.ok("File shared successfully with " + request.getEmail());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails) {

        try {
            Resource resource = fileSharingService.downloadFile(id, userDetails.getUsername());
            String fileName = fileSharingService.getFileName(id);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        }
    }
}
