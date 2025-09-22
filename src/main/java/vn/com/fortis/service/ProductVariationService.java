package vn.com.fortis.service;

import vn.com.fortis.domain.dto.request.product.CreateProductVariationRequestDto;
import vn.com.fortis.domain.dto.request.product.UpdateProductVariationRequestDto;
import vn.com.fortis.domain.dto.response.product.ProductVariationResponseDto;

import java.util.List;

public interface ProductVariationService {

    List<ProductVariationResponseDto> getProductVariationsByProductId(Long productId);

    ProductVariationResponseDto getProductVariationById(Long productVariationId);

    ProductVariationResponseDto createProductVariation(CreateProductVariationRequestDto request);

    ProductVariationResponseDto updateProductVariation(UpdateProductVariationRequestDto request);

    void deleteProductVariation(Long productVariationId);

}
