package com.ecommerce.project.sevice.cart;

import com.ecommerce.project.model.Cart;

import java.math.BigDecimal;

public interface ICartService  {

    Cart getCart(Long Id);
    void clearCart(Long Id);
    BigDecimal getTotalPrice(Long Id);
}
