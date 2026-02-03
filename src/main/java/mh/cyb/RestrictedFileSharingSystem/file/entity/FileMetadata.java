package mh.cyb.RestrictedFileSharingSystem.file.entity;

import jakarta.persistence.*;
import mh.cyb.RestrictedFileSharingSystem.auth.entity.User;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "file_metadata")
public class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String fileName;
    private String storedFileName;
    private LocalDateTime uploadTime;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToMany
    @JoinTable(name = "file_shares", joinColumns = @JoinColumn(name = "file_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> sharedWith = new HashSet<>();

    public FileMetadata() {
    }

    public FileMetadata(String fileName, String storedFileName, User owner) {
        this.fileName = fileName;
        this.storedFileName = storedFileName;
        this.owner = owner;
        this.uploadTime = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStoredFileName() {
        return storedFileName;
    }

    public void setStoredFileName(String storedFileName) {
        this.storedFileName = storedFileName;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<User> getSharedWith() {
        return sharedWith;
    }

    public void setSharedWith(Set<User> sharedWith) {
        this.sharedWith = sharedWith;
    }
}
