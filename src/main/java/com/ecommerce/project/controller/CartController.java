package com.ecommerce.project.controller;


import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Cart;
import com.ecommerce.project.response.APIResponse;
import com.ecommerce.project.sevice.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private ICartService cartService;

    @GetMapping("/my-cart/{cartId}")
    public ResponseEntity<APIResponse> getCart(@PathVariable  Long cartId) {
        try {
            Cart cart  = cartService.getCart(cartId);
            return  ResponseEntity.ok(new APIResponse("Success!!" , cart));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null ));
        }
    }

    @DeleteMapping("/clear/{cartId}")
    public  ResponseEntity<APIResponse> clearCart(@PathVariable Long cartId) {
        try {
            cartService.clearCart(cartId);
            return  ResponseEntity.ok(new APIResponse("Cart cleaned!!" , null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null ));
        }
    }

    @GetMapping("/{cartId}/cart/totalPrice")
    public  ResponseEntity<APIResponse> getTotalAmount(@PathVariable Long cartId) {
        try {
            BigDecimal totalPrice = cartService.getTotalPrice(cartId);
            return  ResponseEntity.ok(new APIResponse("TotalPrice" , totalPrice));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null ));
        }
    }
}
