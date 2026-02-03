package mh.cyb.RestrictedFileSharingSystem.file.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import mh.cyb.RestrictedFileSharingSystem.auth.entity.User;
import mh.cyb.RestrictedFileSharingSystem.auth.repository.UserRepository;
import mh.cyb.RestrictedFileSharingSystem.file.entity.FileMetadata;
import mh.cyb.RestrictedFileSharingSystem.file.repository.FileMetadataRepository;

import java.util.UUID;

@Service
public class FileSharingService {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private FileMetadataRepository fileMetadataRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public FileMetadata uploadFile(MultipartFile file, String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!owner.isVerified()) {
            throw new RuntimeException("User not verified");
        }

        String storedFileName = fileStorageService.store(file);

        FileMetadata metadata = new FileMetadata(
                file.getOriginalFilename(),
                storedFileName,
                owner);

        return fileMetadataRepository.save(metadata);
    }

    @Transactional
    public void shareFile(UUID fileId, String ownerEmail, String shareEmail) {
        FileMetadata file = fileMetadataRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        if (!file.getOwner().getEmail().equals(ownerEmail)) {
            throw new RuntimeException("Only the owner can share this file");
        }

        User shareUser = userRepository.findByEmail(shareEmail)
                .orElseThrow(() -> new RuntimeException("User to share with not found"));

        if (!shareUser.isVerified()) {
            throw new RuntimeException("Cannot share with unverified user");
        }

        file.getSharedWith().add(shareUser);
        fileMetadataRepository.save(file);
    }

    public Resource downloadFile(UUID fileId, String requestorEmail) {
        FileMetadata file = fileMetadataRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        boolean isOwner = file.getOwner().getEmail().equals(requestorEmail);
        boolean isShared = file.getSharedWith().stream()
                .anyMatch(u -> u.getEmail().equals(requestorEmail));

        if (!isOwner && !isShared) {
            throw new RuntimeException("Access denied: You do not have permission to download this file");
        }

        return fileStorageService.loadAsResource(file.getStoredFileName());
    }

    public String getFileName(UUID id) {
        FileMetadata metadata = fileMetadataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));
        return metadata.getFileName();
    }
}
