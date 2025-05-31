package com.ecommerce.project.controller;

import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Image;
import com.ecommerce.project.request.ImageRequest;
import com.ecommerce.project.response.APIResponse;
import com.ecommerce.project.sevice.image.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private IImageService iImageService;

    @PostMapping("/image/upload")
    public ResponseEntity<APIResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam  Long productId){
        try {
            List<ImageRequest> imgReq = iImageService.saveImage(files, productId);
            return ResponseEntity.ok(new APIResponse("Upload Success", imgReq));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse("Upload Failed", e.getMessage()));
        }
    }

    @GetMapping("/image/download/{ImageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable  Long ImageId) throws SQLException {
        Image image = iImageService.getImageById(ImageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFilePath())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"" ).body(resource);
    }

    @PutMapping("/image/{ImageId}/update")
    public ResponseEntity<APIResponse> updateImage(@PathVariable Long ImageId,  @RequestParam MultipartFile file) {
        try {
            Image image = iImageService.getImageById(ImageId);
            if(image != null) {
                iImageService.updateImage(file, ImageId);
                return ResponseEntity.ok(new APIResponse("Update Success!!", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse("Update Failed!!", null));
    }

    @DeleteMapping("/image/{ImageId}/delete")
    public ResponseEntity<APIResponse> deleteImage(@PathVariable Long ImageId) {
        try {
            Image image = iImageService.getImageById(ImageId);
            if(image != null) {
                iImageService.deleteImageById(ImageId);
                return ResponseEntity.ok(new APIResponse("Delete Success!!", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse("Delete Failed!!", null));
    }
}
