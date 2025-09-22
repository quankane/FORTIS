package vn.com.fortis.controller;

import vn.com.fortis.base.ResponseUtil;
import vn.com.fortis.base.RestApiV1;
import vn.com.fortis.constant.SuccessMessage;
import vn.com.fortis.constant.UrlConstant;
import vn.com.fortis.domain.dto.pagination.PaginationRequestDto;
import vn.com.fortis.domain.dto.request.promotion.PromotionRequestDto;
import vn.com.fortis.service.PromotionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@Validated
@RequiredArgsConstructor
@Slf4j(topic = "PROMOTION-CONTROLLER")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PromotionController {

    PromotionService promotionService;

    @Operation(
            summary = "Thêm khuyến mãi",
            description = "Dùng để admin thêm khuyến mãi với role Admin",
            security = @SecurityRequirement(name = "Bearer Token")
    )
    @PostMapping(UrlConstant.Promotion.ADD_PROMOTION)
    public ResponseEntity<?> addPromotion (@Valid @RequestBody PromotionRequestDto promotionRequestDto){
        return ResponseUtil.success(
                SuccessMessage.Promotion.ADD_PROMOTION_SUCCESS,
                promotionService.addPromotion(promotionRequestDto)
        );
    }

    @Operation(
            summary = "Lấy khuyến mãi theo ID",
            description = "Dùng để admin lấy danh mục theo ID với role Admin",
            security = @SecurityRequirement(name = "Bearer Token")
    )
    @GetMapping(UrlConstant.Promotion.GET_PROMOTION_BY_ID)
    public ResponseEntity<?> getPromotionById(@PathVariable Long promotionId){
        return ResponseUtil.success(
                SuccessMessage.Promotion.GET_PROMOTION_SUCCESS,
                promotionService.getPromotionById(promotionId)
        );
    }

    @Operation(
            summary = "Cập nhật khuyến mãi",
            description = "Dùng để admin cập nhật khuyến mãi theo ID và request form với role Admin",
            security = @SecurityRequirement(name = "Bearer Token")
    )
    @PutMapping(UrlConstant.Promotion.UPDATE_PROMOTION)
    public ResponseEntity<?> updatePromotion(@PathVariable("promotionId") Long promotionId, @Valid @RequestBody PromotionRequestDto promotionRequestDto){
        return ResponseUtil.success(
                SuccessMessage.Promotion.UPDATE_PROMOTION_SUCCESS,
                promotionService.updatePromotion(promotionId, promotionRequestDto)
        );
    }

    @Operation(
            summary = "Xóa khuyến mãi theo Id",
            description = "Dùng để admin xóa khuyến mãi theo ID với role Admin",
            security = @SecurityRequirement(name = "Bearer Token")
    )
    @DeleteMapping(UrlConstant.Promotion.DELETE_PROMOTION)
    public ResponseEntity<?> deletePromotion(@PathVariable Long promotionId){
        promotionService.deletePromotion(promotionId);
        return ResponseUtil.success(
                HttpStatus.NO_CONTENT,
                SuccessMessage.Promotion.DELETE_PROMOTION_SUCCESS
        );
    }

    @Operation(
            summary = "Lấy khuyến mãi theo mã code",
            description = "Dùng để admin lấy khuyến mãi theo mã code với role Admin",
            security = @SecurityRequirement(name = "Bearer Token")
    )
    @GetMapping(UrlConstant.Promotion.GET_PROMOTION_BY_CODE)
    public ResponseEntity<?> getPromotionByPromotionCode(@PathVariable String promotionCode){
        return ResponseUtil.success(
                SuccessMessage.Promotion.GET_PROMOTION_SUCCESS,
                promotionService.getPromotionByPromotionCode(promotionCode)
        );
    }

    @Operation(
            summary = "Lọc khuyến mãi theo nhiều tiêu chí",
            description = "Lọc khuyến mãi theo kiểu, ngày bđ, ngày kt, sort by percent với phân trang"
    )
    @GetMapping(UrlConstant.Promotion.FILTER_PROMOTION)
    public ResponseEntity<?> filterProducts(
            @RequestParam(defaultValue = "1", required = false) Integer pageNum,
            @RequestParam(defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(required = false) @Schema(example = "asc") String sortByPrice,
            @RequestParam(required = false)
            @Schema(example = "type:active,startDate:2020-01-01,endDate:2025-12-31,status:active")
            String ... search) {
        PaginationRequestDto paginationRequest = new PaginationRequestDto(pageNum, pageSize);
        return ResponseUtil.success(
                SuccessMessage.Product.GET_PRODUCT_SUCCESS,
                promotionService.filterPromotions(paginationRequest, sortByPrice, search));
    }
}
