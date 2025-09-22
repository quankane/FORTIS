package vn.com.fortis.domain.dto.response.promotion;

import vn.com.fortis.constant.promotion.PromotionStatus;
import vn.com.fortis.constant.promotion.PromotionType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionResponseDto {

    Long id;
    String promotionCode;
    String description;
    PromotionType type;
    PromotionStatus status;
    LocalDate startDate;
    LocalDate endDate;

    // --- Order promotion ---
    Float minPriceOrder;
    Float maxPriceOrder;

    Float discountPercent;

    // --- Category promotion ---
    Long categoryId;
}
