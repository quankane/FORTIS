package vn.com.fortis.service;

import org.springframework.web.multipart.MultipartFile;

import vn.com.fortis.domain.dto.pagination.PaginationRequestDto;
import vn.com.fortis.domain.dto.pagination.PaginationResponseDto;
import vn.com.fortis.domain.dto.request.product.ProductRequestDto;
import vn.com.fortis.domain.dto.request.product.UpdateProductRequestDto;
import vn.com.fortis.domain.dto.response.product.ProductResponseDto;

public interface ProductService {

    ProductResponseDto getProductById(Long id);

    PaginationResponseDto<ProductResponseDto> getAllProducts(PaginationRequestDto paginationRequest);

    ProductResponseDto createProduct(ProductRequestDto request, MultipartFile[] images);

    ProductResponseDto updateProduct(Long productId, UpdateProductRequestDto request, MultipartFile[] images);

    void deleteProduct(Long productId);

    PaginationResponseDto<ProductResponseDto> getProductsByCategoryId(Long categoryId,
                                                                      PaginationRequestDto paginationRequest);

    PaginationResponseDto<ProductResponseDto> filterProducts(
            PaginationRequestDto paginationRequest, String sortByPrice, String search);

}
