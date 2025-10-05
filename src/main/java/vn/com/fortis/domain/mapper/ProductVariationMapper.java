package vn.com.fortis.domain.mapper;

import vn.com.fortis.domain.entity.product.ProductVariation;
import vn.com.fortis.domain.dto.request.product.CreateProductVariationRequestDto;
import vn.com.fortis.domain.dto.request.product.UpdateProductVariationRequestDto;
import vn.com.fortis.domain.dto.response.product.ProductVariationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProductVariationMapper {

    List<ProductVariationResponseDto> toListProductVariationResponseDto(List<ProductVariation> productVariations);

    ProductVariationResponseDto toProductVariationResponseDto(ProductVariation productVariation);

    ProductVariation toProductVariation(CreateProductVariationRequestDto request);

    void updateProductVariationFromDto(
            @MappingTarget ProductVariation productVariation,
            UpdateProductVariationRequestDto request);
}
