package com.ecommerce.project.sevice.cart;

import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Cart;
import com.ecommerce.project.model.CartItem;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.repositories.CartItemRepository;
import com.ecommerce.project.repositories.CartRepository;
import com.ecommerce.project.sevice.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements ICartItemService{

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    ICartService iCartService;

    private final AtomicLong cartIdGenerator = new AtomicLong(0);
    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {

            Cart cart = iCartService.getCart(cartId);
            Product product = iProductService.getProductById(productId);
            CartItem cartItem = cart.getItems().stream()
                    .filter(item -> item.getProduct().getProductId().equals(productId)).findFirst().orElse(new CartItem());
            if(cartItem.getId() == null){
                cartItem.setProduct(product);
                cartItem.setQuantity(quantity);
                cartItem.setUnitPrice(product.getPrice());
            }
            else {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
            }
            cartItem.setTotalPrice();
            cart.addItem(cartItem);
            cartItemRepository.save(cartItem);
            cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = iCartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, productId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = iCartService.getCart(cartId);
        cart.getItems().stream().filter(item -> item.getProduct().getProductId().equals(productId)).findFirst()
                .ifPresent(item -> {item.setQuantity(quantity);
                                    item.setUnitPrice(item.getProduct().getPrice());
                                    item.setTotalPrice();
                });

        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = iCartService.getCart(cartId);
       return  cart.getItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId)).findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("Product not found"));
    }


    @Override
    public Long initializeNewCart() {
        Cart newCart= new Cart();
//        Long newCartId  = cartIdGenerator.incrementAndGet();
//        newCart.setId(newCartId);
        return  cartRepository.save(newCart).getId();
    }

}
