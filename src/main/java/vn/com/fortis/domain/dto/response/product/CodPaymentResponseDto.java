package vn.com.fortis.domain.dto.response.product;

import vn.com.fortis.constant.OrderStatus;
import vn.com.fortis.domain.entity.product.payment.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Response DTO cho thanh toán COD")
public class CodPaymentResponseDto {

    Long orderId;

    String orderNumber;

    Double totalAmount;

    OrderStatus orderStatus;

    PaymentStatus paymentStatus;

    String paymentId;

    String message;
}
