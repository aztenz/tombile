package com.n2o.tombile.image;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable int id) {
        Image image = imageService.getImage(id);

        if (image != null) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + image.getName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, image.getType())
                    .body(image.getData());
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
