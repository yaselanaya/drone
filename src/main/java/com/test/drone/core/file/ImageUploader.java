package com.test.drone.core.file;

import com.test.drone.core.base.IMessages;
import com.test.drone.core.exception.DroneException;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Component
@Slf4j
public class ImageUploader implements IImageUploader{

    @Value(value = "${spring.servlet.multipart.location}")
    private String uploadPath;

    private final IMessages messages;

    public ImageUploader(IMessages messages) {
        this.messages = messages;
    }

    @Override
    public String updateImage(MultipartFile image, String previousImage, String receivedImage)
            throws DroneException {

        if (Objects.isNull(image))
            return receivedImage;

        String imageUploaded = uploadImage(image);

        if (Strings.isNotBlank(previousImage) && !previousImage.equals(imageUploaded))
            deleteFile(previousImage);

        return imageUploaded;
    }

    private String uploadImage(MultipartFile file) throws DroneException {
        String fileName = Objects.nonNull(file.getOriginalFilename()) ? file.getOriginalFilename() : file.getName();
        String path = uploadFile(file, uploadPath + UUID.randomUUID().toString() + "-" + fileName);

        if (path.equals(Strings.EMPTY))
            throw new DroneException(HttpStatus.NOT_FOUND, messages.getMessage("image.error.not.found"));

        return path;
    }

    public String uploadFile(MultipartFile file, String path) {
        try {
            byte[] info = file.getBytes();

            File destination = new File(path);

            boolean isCreated = destination.getParentFile().exists();
            if (!isCreated)
                isCreated = destination.getParentFile().mkdirs();

            if (isCreated) {
                Path dir = Paths.get(destination.getAbsolutePath());
                Files.write(dir, info);
            }

            return path;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return "";
        }
    }

    private void deleteFile(String path) {
        Path file = Paths.get(path);
        try {
            Files.delete(file);
        } catch (IOException e) {
            log.error("File does not exist", e);
        }
    }
}
