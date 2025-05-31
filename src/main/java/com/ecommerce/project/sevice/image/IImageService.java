package com.ecommerce.project.sevice.image;

import com.ecommerce.project.model.Image;
import com.ecommerce.project.request.ImageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {

    Image getImageById(Long ImageId);
    void deleteImageById(Long ImageId);
    List<ImageRequest> saveImage(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file, Long ImageId);
}
