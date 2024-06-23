package com.n2o.tombile.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public String saveImageWithPath(MultipartFile file) {
        String imagePath = "/api/images/";
        try {
            Image image = saveImage(file);
            imagePath += image.getId();
        } catch (IOException e) {
            imagePath = null;
        }
        return imagePath;
    }

    private Image saveImage(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setData(file.getBytes());
        image.setName(file.getName());
        image.setType(file.getContentType());

        return imageRepository.save(image);
    }

    public Image getImage(int id) {
        return imageRepository.findById(id).orElse(null);
    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf('.') + 1);
    }
}
