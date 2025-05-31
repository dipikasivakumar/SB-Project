package com.ecommerce.project.controller;


import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.response.APIResponse;
import com.ecommerce.project.sevice.cart.ICartItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cartItem")
public class CartItemController {

    @Autowired
    private ICartItemService iCartItemService;

    @PostMapping("/add")
    @Transactional
    public ResponseEntity<APIResponse> addItemToCart(@RequestParam(required = false) Long cartId, @RequestParam Long itemId, @RequestParam int quantity) {
        try {
            if(cartId == null) {
                cartId = iCartItemService.initializeNewCart();
            }
            iCartItemService.addItemToCart(cartId, itemId, quantity);
            return  ResponseEntity.ok(new APIResponse("Item added to Cart!!" , null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null ));
        }
    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<APIResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        try {
            iCartItemService.removeItemFromCart(cartId, itemId);
            return  ResponseEntity.ok(new APIResponse("Item removed from cart!!" , null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null ));
        }
    }

    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<APIResponse> updateItemQuantity(@PathVariable Long cartId, @PathVariable Long itemId, @RequestParam int quantity) {
        try {
            iCartItemService.updateItemQuantity(cartId, itemId, quantity);
            return  ResponseEntity.ok(new APIResponse("Item Update cart!!" , null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null ));
        }
    }


}
