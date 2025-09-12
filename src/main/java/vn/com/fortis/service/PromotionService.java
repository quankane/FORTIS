package vn.com.fortis.service;

import vn.com.fortis.domain.dto.request.promotion.PromotionRequestDto;
import vn.com.fortis.domain.dto.response.promotion.PromotionResponseDto;

import java.util.List;

public interface PromotionService {

    PromotionResponseDto addPromotion(PromotionRequestDto requestDto);

    PromotionResponseDto updatePromotion(Long id, PromotionRequestDto requestDto);

    PromotionResponseDto getPromotionById(Long id);

    List<PromotionResponseDto> getAllPromotion();

    void deletePromotion(Long id);

    PromotionResponseDto getPromotionByPromotionCode(String promotionCode);
}
