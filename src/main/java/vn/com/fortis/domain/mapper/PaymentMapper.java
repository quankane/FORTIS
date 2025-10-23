package vn.com.fortis.domain.mapper;

import vn.com.fortis.domain.dto.response.payment.PaymentResponseDto;
import vn.com.fortis.domain.entity.product.payment.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface PaymentMapper {

    PaymentResponseDto paymentToPaymentResponseDto(Payment payment);
}
