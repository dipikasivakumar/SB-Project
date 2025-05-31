package com.ecommerce.project.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Data
@RequestMapping("/api")
public class APIResponse {
    private String message;
    private Object data;

}
