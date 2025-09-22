package vn.com.fortis.controller;

import vn.com.fortis.base.ResponseUtil;
import vn.com.fortis.base.RestApiV1;
import vn.com.fortis.constant.SuccessMessage;
import vn.com.fortis.constant.UrlConstant;
import vn.com.fortis.domain.dto.request.product.CreateProductVariationRequestDto;
import vn.com.fortis.domain.dto.request.product.UpdateProductVariationRequestDto;
import vn.com.fortis.domain.dto.response.product.ProductVariationResponseDto;
import vn.com.fortis.service.ProductVariationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestApiV1
@RequiredArgsConstructor
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "admin-product-variation-controller", description = "APIs for managing product variations")
public class ProductVariationController {

        ProductVariationService productVariationService;

        @GetMapping(UrlConstant.Product.GET_PRODUCT_VARIATIONS_BY_PRODUCT_ID)
        @Operation(
                summary = "Lấy danh sách biến thể theo ID sản phẩm",
                description = "Lấy tất cả các biến thể của một sản phẩm cụ thể",
                security = @SecurityRequirement(name = "Bearer Token")
        )
        public ResponseEntity<?> getProductVariationsByProductId(
                        @Parameter(description = "ID sản phẩm", example = "1") @PathVariable Long productId) {

                List<ProductVariationResponseDto> variations = productVariationService
                                .getProductVariationsByProductId(productId);
                return ResponseUtil.success(
                                HttpStatus.OK,
                                SuccessMessage.Product.GET_PRODUCT_VARIATIONS_SUCCESS,
                                variations);
        }

        @GetMapping(UrlConstant.Product.GET_PRODUCT_VARIATION_BY_ID)
        @Operation(
                summary = "Lấy thông tin biến thể theo ID",
                description = "Lấy thông tin chi tiết của một biến thể sản phẩm theo ID",
                security = @SecurityRequirement(name = "Bearer Token")
        )
        public ResponseEntity<?> getProductVariationById(
                        @Parameter(description = "ID biến thể sản phẩm", example = "1") @PathVariable Long variationId) {

                ProductVariationResponseDto variation = productVariationService.getProductVariationById(variationId);
                return ResponseUtil.success(
                                HttpStatus.OK,
                                SuccessMessage.Product.GET_PRODUCT_VARIATION_SUCCESS,
                                variation);
        }

        @PostMapping(value = UrlConstant.Product.CREATE_PRODUCT_VARIATION, consumes = "multipart/form-data")
        @Operation(
                summary = "Tạo mới biến thể sản phẩm",
                description = "Tạo một biến thể mới cho sản phẩm đã có",
                security = @SecurityRequirement(name = "Bearer Token")
        )
        public ResponseEntity<?> createProductVariation(
                        @Valid @ModelAttribute CreateProductVariationRequestDto request) {
                ProductVariationResponseDto createdVariation = productVariationService.createProductVariation(request);
                return ResponseUtil.success(
                                HttpStatus.CREATED,
                                SuccessMessage.Product.CREATE_PRODUCT_VARIATION_SUCCESS,
                                createdVariation);
        }

        @PutMapping(value = UrlConstant.Product.UPDATE_PRODUCT_VARIATION, consumes = "multipart/form-data")
        @Operation(
                summary = "Cập nhật biến thể sản phẩm",
                description = "Cập nhật thông tin của một biến thể sản phẩm đã có",
                security = @SecurityRequirement(name = "Bearer Token")
        )
        public ResponseEntity<?> updateProductVariation(
                        @Valid @ModelAttribute UpdateProductVariationRequestDto request) {

                ProductVariationResponseDto updatedVariation = productVariationService.updateProductVariation(request);
                return ResponseUtil.success(
                                HttpStatus.OK,
                                SuccessMessage.Product.UPDATE_PRODUCT_VARIATION_SUCCESS,
                                updatedVariation);
        }

        @DeleteMapping(UrlConstant.Product.DELETE_PRODUCT_VARIATION)
        @Operation(
                summary = "Xóa biến thể sản phẩm",
                description = "Xóa một biến thể sản phẩm theo ID",
                security = @SecurityRequirement(name = "Bearer Token")
        )
        @ApiResponse(responseCode = "200", description = "Xóa biến thể sản phẩm thành công")
        public ResponseEntity<?> deleteProductVariation(
                        @Parameter(description = "ID biến thể sản phẩm", example = "1") @PathVariable Long variationId) {

                productVariationService.deleteProductVariation(variationId);
                return ResponseUtil.success(
                                HttpStatus.OK,
                                SuccessMessage.Product.DELETE_PRODUCT_VARIATION_SUCCESS);
        }
}