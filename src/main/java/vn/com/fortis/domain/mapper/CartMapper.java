package vn.com.fortis.domain.mapper;

import vn.com.fortis.domain.dto.response.cart.CartResponse;
import vn.com.fortis.domain.entity.product.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        uses = {CartItemMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface CartMapper {

    @Mapping(target = "items", source = "cart.cartItems")
    @Mapping(target = "lastUpdated", source = "cart.updatedAt")
    CartResponse cartToCartResponse(Cart cart);
}
