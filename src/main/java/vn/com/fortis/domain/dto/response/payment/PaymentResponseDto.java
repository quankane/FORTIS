package vn.com.fortis.domain.dto.response.payment;

import vn.com.fortis.domain.entity.product.payment.PaymentGateway;
import vn.com.fortis.domain.entity.product.payment.PaymentStatus;
import vn.com.fortis.domain.entity.product.payment.PaymentType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponseDto {

    Long amount;

    PaymentGateway gateway;

    PaymentType type;

    PaymentStatus status;

    Date expireAt;
}
