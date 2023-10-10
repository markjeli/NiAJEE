package com.jelinski.niajee.user.service;

import com.jelinski.niajee.user.entity.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class UserPortraitService {
    private String filePath;
    public UserPortraitService(String filePath) {
        this.filePath = filePath;
    }
    public void savePortrait(User user) {
        Path newFolder = Paths.get(this.filePath);
        File targetFile = new File(newFolder + "\\" + user.getId() + ".png");
        try {
            OutputStream outStream = new FileOutputStream(targetFile);
            outStream.write(user.getPortrait());
            outStream.close();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public void deletePortrait(UUID id) {
        File targetFile = new File(this.filePath + "\\" + id + ".png");
        targetFile.delete();
    }
}
