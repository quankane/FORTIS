package vn.com.fortis.service;

import vn.com.fortis.domain.dto.pagination.PaginationRequestDto;
import vn.com.fortis.domain.dto.pagination.PaginationResponseDto;
import vn.com.fortis.domain.dto.request.product.CreateProductRequestDto;
import vn.com.fortis.domain.dto.request.product.UpdateProductRequestDto;
import vn.com.fortis.domain.dto.response.product.ProductResponseDto;

public interface ProductService {

    ProductResponseDto getProductById(Long id);

    ProductResponseDto createProduct(CreateProductRequestDto request);

    ProductResponseDto updateProduct(Long productId, UpdateProductRequestDto request);

    void deleteProduct(Long productId);

    PaginationResponseDto<ProductResponseDto> getProductsByCategoryId(Long categoryId,
            PaginationRequestDto paginationRequest);

    PaginationResponseDto<ProductResponseDto> filterProducts(
            PaginationRequestDto paginationRequest, String sortByPrice, String... search);

}
