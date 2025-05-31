package com.ecommerce.project.sevice.image;

import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Image;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.repositories.ImageRepository;
import com.ecommerce.project.request.ImageRequest;
import com.ecommerce.project.sevice.product.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements IImageService{

    @Autowired
    ImageRepository imageRepository;
    @Autowired
    ProductServiceImpl productServiceImpl;

    @Override
    public Image getImageById(Long Id) {
        return imageRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Image not Found"));
    }

    @Override
    public void deleteImageById(Long Id) {
         imageRepository.findById(Id).ifPresentOrElse(imageRepository :: delete, () -> {throw new ResourceNotFoundException("Image not Found with id: "+ Id);});
    }

    @Override
    public List<ImageRequest> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productServiceImpl.getProductById(productId);
        List<ImageRequest> savedImageReq = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFilePath(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);
                String buildDownloadURL = "/api/images/image/download";
                String downloadURL = buildDownloadURL + image.getImageId();
                image.setDownloadURL(downloadURL);
                Image savedImage = imageRepository.save(image);
                savedImage.setDownloadURL(buildDownloadURL + savedImage.getImageId());
                imageRepository.save(savedImage);

                ImageRequest img = new ImageRequest();
                img.setImageId(savedImage.getImageId());
                img.setImageName(savedImage.getFileName());
                img.setDownloadURL(savedImage.getDownloadURL());
                savedImageReq.add(img);
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageReq;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {

        try {
            Image image = getImageById(imageId);
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
