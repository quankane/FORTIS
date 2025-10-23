package vn.com.fortis.service;

import vn.com.fortis.domain.dto.request.cart.CartRequest;
import vn.com.fortis.domain.dto.response.cart.CartResponse;

public interface CartService {

    CartResponse addToCart(String email, CartRequest cartRequest);

    CartResponse getCart(String email);

    CartResponse removeItem(String email, Long productVariationId);

    void clearCart(String email);

    CartResponse updateQuantity(String email, CartRequest cartRequest);

}
