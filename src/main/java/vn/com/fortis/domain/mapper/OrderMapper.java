package vn.com.fortis.domain.mapper;

import vn.com.fortis.domain.dto.response.product.OrderResponseDto;
import vn.com.fortis.domain.entity.product.Order;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface OrderMapper {

    OrderResponseDto orderToOrderResponseDto(Order order);

    OrderResponseDto orderToOrderResponse(Order order);

}
