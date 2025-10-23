package vn.com.fortis.domain.mapper;

import vn.com.fortis.domain.dto.response.cart.CartItemResponse;
import vn.com.fortis.domain.entity.product.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface CartItemMapper {

    @Mapping(target = "productVariationId", source = "productVariation.id")
    CartItemResponse cartItemToCartItemResponse(CartItem cartItem);

}
