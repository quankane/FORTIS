package vn.com.fortis.service;

import vn.com.fortis.domain.dto.pagination.PaginationRequestDto;
import vn.com.fortis.domain.dto.pagination.PaginationResponseDto;
import vn.com.fortis.domain.dto.request.promotion.PromotionRequestDto;
import vn.com.fortis.domain.dto.response.promotion.PromotionResponseDto;

import java.util.List;

public interface PromotionService {

    PromotionResponseDto addPromotion(PromotionRequestDto requestDto);

    PromotionResponseDto updatePromotion(Long id, PromotionRequestDto requestDto);

    PromotionResponseDto getPromotionById(Long id);

    void deletePromotion(Long id);

    PromotionResponseDto getPromotionByPromotionCode(String promotionCode);

    PaginationResponseDto<PromotionResponseDto> filterPromotions(PaginationRequestDto paginationRequest, String sortByPrice, String... search);

}