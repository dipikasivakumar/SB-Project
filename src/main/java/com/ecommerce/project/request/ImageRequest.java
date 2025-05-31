package com.ecommerce.project.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageRequest {
    private Long ImageId;
    private String imageName;
    private String downloadURL;

}
