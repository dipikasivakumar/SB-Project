package com.ecommerce.project.sevice.cart;

import com.ecommerce.project.model.CartItem;

public interface ICartItemService  {

    void addItemToCart(Long cartId, Long productId,int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId,int quantity);

    CartItem getCartItem(Long cartId, Long productId);

    Long initializeNewCart();
}
