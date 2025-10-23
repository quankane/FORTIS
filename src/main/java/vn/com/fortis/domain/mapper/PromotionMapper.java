package vn.com.fortis.domain.mapper;

import vn.com.fortis.domain.dto.request.promotion.PromotionRequestDto;
import vn.com.fortis.domain.dto.response.promotion.PromotionResponseDto;
import vn.com.fortis.domain.entity.product.Promotion;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface PromotionMapper {

    Promotion promotionRequestDtoToPromotion (PromotionRequestDto requestDto);

    void updatePromotionFromDto(PromotionRequestDto requestDto, @MappingTarget Promotion promotion);

    @Mapping(target = "categoryId", source = "category.id")
    PromotionResponseDto promotionToPromotionResponseDto(Promotion promotion);

}
