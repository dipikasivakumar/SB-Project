package com.ecommerce.project.sevice.cart;

import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Cart;
import com.ecommerce.project.repositories.CartItemRepository;
import com.ecommerce.project.repositories.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements  ICartService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public Cart getCart(Long Id) {
        Cart cart = cartRepository.findById(Id).orElseThrow(()-> new ResourceNotFoundException("Cart Not Found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void clearCart(Long Id) {
        Cart cart = getCart(Id);
        cartItemRepository.deleteAllByCartId(Id);
        cart.getItems().clear();
        cartRepository.deleteById(Id);

    }

    @Override
    public BigDecimal getTotalPrice(Long Id) {
        Cart cart = getCart(Id);
        return cart.getTotalAmount();
    }
}
