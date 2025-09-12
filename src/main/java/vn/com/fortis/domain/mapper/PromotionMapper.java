package vn.com.fortis.domain.mapper;

import vn.com.fortis.domain.dto.request.promotion.PromotionRequestDto;
import vn.com.fortis.domain.dto.response.promotion.PromotionResponseDto;
import vn.com.fortis.domain.entity.product.Promotion;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface PromotionMapper {

    Promotion promotionRequestDtoToPromotion (PromotionRequestDto requestDto);

    void updatePromotionFromDto(PromotionRequestDto requestDto, @MappingTarget Promotion promotion);

    PromotionResponseDto promotionToPromotionResponseDto(Promotion promotion);

}
