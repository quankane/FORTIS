package vn.com.fortis.service.impl;

import vn.com.fortis.constant.ErrorMessage;
import vn.com.fortis.domain.dto.request.cart.CartRequest;
import vn.com.fortis.domain.dto.response.cart.CartResponse;
import vn.com.fortis.domain.entity.product.Cart;
import vn.com.fortis.domain.entity.product.CartItem;
import vn.com.fortis.domain.entity.product.ProductVariation;
import vn.com.fortis.domain.entity.user.User;
import vn.com.fortis.domain.mapper.CartMapper;
import vn.com.fortis.exception.InvalidDataException;
import vn.com.fortis.exception.ResourceNotFoundException;
import vn.com.fortis.repository.CartItemRepository;
import vn.com.fortis.repository.CartRepository;
import vn.com.fortis.repository.ProductVariationRepository;
import vn.com.fortis.repository.UserRepository;
import vn.com.fortis.service.CartService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j(topic = "CART-SERVICE")
public class CartServiceImpl implements CartService {

    CartRepository cartRepository;

    ProductVariationRepository productVariationRepository;

    CartMapper cartMapper;

    CartItemRepository cartItemRepository;

    UserRepository userRepository;

    @Transactional
    @Override
    public CartResponse addToCart(String email, CartRequest cartRequest) {

        User user = userRepository.findByUsernameAndIsDeletedFalse(email).orElseThrow(
                () -> new ResourceNotFoundException(ErrorMessage.User.ERR_USER_NOT_EXISTED));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.Cart.ERR_CART_NOT_FOUND));

        if (cartRequest.quantity() <= 0) {
            throw new InvalidDataException(ErrorMessage.Cart.ERR_CART_QUANTITY_INVALID);
        }

        ProductVariation productVariation = productVariationRepository.findByIdAndIsDeletedFalse(cartRequest.variantId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.Product.ERR_PRODUCT_VARIATION_NOT_EXISTED));

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProductVariation().getId().equals(cartRequest.variantId()))
                .findFirst()
                .orElse(null);

        if(productVariation.getInventoryQuantity() < (cartRequest.quantity() + (cartItem != null ? cartItem.getQuantity() : 0))) {
            throw new InvalidDataException(ErrorMessage.Cart.ERR_CART_QUANTITY_INVALID);
        }

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + cartRequest.quantity());
        }
        else {
            CartItem newItem = CartItem.builder()
                    .quantity(cartRequest.quantity())
                    .cart(cart)
                    .productVariation(productVariation)
                    .build();
            cart.getCartItems().add(newItem);
        }

        Cart updatedCart = cartRepository.save(cart);

        return cartMapper.cartToCartResponse(updatedCart);
    }

    @Override
    public CartResponse getCart(String email) {
        User user = userRepository.findByUsernameAndIsDeletedFalse(email).orElseThrow(
                () -> new ResourceNotFoundException(ErrorMessage.User.ERR_USER_NOT_EXISTED));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.Cart.ERR_CART_NOT_FOUND));


        return cartMapper.cartToCartResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse removeItem(String email, Long productVariationId) {
        User user = userRepository.findByUsernameAndIsDeletedFalse(email).orElseThrow(
                () -> new ResourceNotFoundException(ErrorMessage.User.ERR_USER_NOT_EXISTED));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.Cart.ERR_CART_NOT_FOUND));


        boolean removed = cart.getCartItems().removeIf(item -> item.getProductVariation().getId().equals(productVariationId));

        if (!removed) {
            throw new InvalidDataException(ErrorMessage.Cart.ERR_CART_ITEM_NOT_EXISTED_IN_CART);
        }

        return cartMapper.cartToCartResponse(cartRepository.save(cart));
    }

    @Override
    public void clearCart(String email) {
        User user = userRepository.findByUsernameAndIsDeletedFalse(email).orElseThrow(
                () -> new ResourceNotFoundException(ErrorMessage.User.ERR_USER_NOT_EXISTED));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.Cart.ERR_CART_NOT_FOUND));

        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public CartResponse updateQuantity(String email, CartRequest cartRequest) {
        if (cartRequest.quantity() <= 0) {
            throw new InvalidDataException(ErrorMessage.Cart.ERR_CART_QUANTITY_INVALID);
        }

        User user = userRepository.findByUsernameAndIsDeletedFalse(email).orElseThrow(
                () -> new ResourceNotFoundException(ErrorMessage.User.ERR_USER_NOT_EXISTED));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.Cart.ERR_CART_NOT_FOUND));

        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProductVariation().getId().equals(cartRequest.variantId()))
                .findFirst()
                .orElse(null);

        if (existingItem == null) {
            throw new InvalidDataException(ErrorMessage.Cart.ERR_CART_ITEM_NOT_EXISTED_IN_CART);
        }

        ProductVariation productVariation = productVariationRepository.findByIdAndIsDeletedFalse(cartRequest.variantId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.Product.ERR_PRODUCT_VARIATION_NOT_EXISTED));

        if(productVariation.getInventoryQuantity() < cartRequest.quantity() ) {
            throw new InvalidDataException(ErrorMessage.Cart.ERR_CART_QUANTITY_INVALID);
        }

        existingItem.setQuantity(cartRequest.quantity());

        cartItemRepository.save(existingItem);

        return cartMapper.cartToCartResponse(cart);
    }
}
