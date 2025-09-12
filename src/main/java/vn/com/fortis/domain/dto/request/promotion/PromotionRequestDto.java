package vn.com.fortis.domain.dto.request.promotion;

import vn.com.fortis.constant.ErrorMessage;
import vn.com.fortis.constant.promotion.PromotionStatus;
import vn.com.fortis.constant.promotion.PromotionType;
import vn.com.fortis.domain.validator.PositiveOrNull;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionRequestDto {

    @NotBlank(message = ErrorMessage.Promotion.ERR_PROMOTION_CODE_NOT_BLANK)
    @Length(min = 6, max = 6, message = ErrorMessage.Promotion.ERR_PROMOTION_CODE_LENGTH)
    String promotionCode;
    @NotNull(message = ErrorMessage.Promotion.ERR_PROMOTION_DESCRIPTION_NOT_NULL)
    String description;

    @NotBlank(message = ErrorMessage.Promotion.ERR_PROMOTION_TYPE_NOT_BLANK)
    PromotionType type;

    @NotBlank(message = ErrorMessage.Promotion.ERR_PROMOTION_STATUS_NOT_BLANK)
    PromotionStatus status;
    @NotEmpty(message = ErrorMessage.Promotion.ERR_PROMOTION_START_DATE_NOT_EMPTY)
    LocalDate startDate;
    @NotEmpty(message = ErrorMessage.Promotion.ERR_PROMOTION_END_DATE_NOT_EMPTY)
    LocalDate endDate;

    @PositiveOrNull
    Float minPriceOrder;
    @PositiveOrNull
    Float maxPriceOrder;

    @NotBlank(message = ErrorMessage.Promotion.ERR_PROMOTION_DISCOUNT_PERCENT_NOT_BLANK)
    @Min(value = 0, message = ErrorMessage.Promotion.ERR_PROMOTION_DISCOUNT_PERCENT_MIN_VALIDATE)
    @Max(value = 100, message = ErrorMessage.Promotion.ERR_PROMOTION_DISCOUNT_PERCENT_MAX_VALIDATE)
    Float discountPercent;

    Long categoryId;
}
