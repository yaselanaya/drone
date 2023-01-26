package com.test.drone.core.file;

import com.test.drone.core.exception.DroneException;
import org.springframework.web.multipart.MultipartFile;

public interface IImageUploader {

    /**
     * Upload image of the medication
     *
     * @param image           The new image to be uploaded.
     * @param previousImage   The path of the previous image to be deleted from {@code path},
     *                        if is empty do not delete any image.
     * @param receivedImage   The image received in the request
     * @throws DroneException Throws an exception if the operation fails
     */
    String updateImage(MultipartFile image, String previousImage, String receivedImage)
            throws DroneException;
}
