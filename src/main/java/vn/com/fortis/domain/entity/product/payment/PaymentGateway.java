package vn.com.fortis.domain.entity.product.payment;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum PaymentGateway {
    @JsonProperty("vnpay")
    VNPAY;

}
