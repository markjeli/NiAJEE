package com.jelinski.niajee.user.service;

import com.jelinski.niajee.controller.servlet.exception.NotFoundException;
import com.jelinski.niajee.user.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class UserPortraitService {
    /**
     * Path to folder where portraits are stored.
     */
    private final String fileStorePath;

    @Inject
    public UserPortraitService(ServletContext servletContext) {
        this.fileStorePath = servletContext.getInitParameter("portrait_path");
    }

    public byte[] getPortrait(User user) throws IOException {
        if (user.getPortraitPath() == null) {
            throw new NotFoundException();
        }
        Path folderPath = Paths.get(user.getPortraitPath());
        try {
            return Files.readAllBytes(folderPath);
        } catch (IOException e) {
            throw new IOException("Error reading portrait", e);
        }
    }

    public void savePortrait(User user, byte[] portrait) throws IOException {
        Path rootPath = Paths.get(this.fileStorePath);
        if (!Files.exists(rootPath)) {
            Files.createDirectory(rootPath);
        }

        Path newFolderPath = rootPath.resolve(user.getId().toString() + ".png");
        try {
            Files.write(newFolderPath, portrait);
            user.setPortraitPath(newFolderPath.toString());
        } catch (IOException e) {
            throw new IOException("Error saving portrait", e);
        }
    }

    public void deletePortrait(User user) throws IOException {
        Path folderPath = Paths.get(this.fileStorePath).resolve(user.getId().toString() + ".png");
        try {
            Files.delete(folderPath);
            user.setPortraitPath(null);
        } catch (IOException e) {
            throw new IOException("Error deleting portrait", e);
        }
    }
}
