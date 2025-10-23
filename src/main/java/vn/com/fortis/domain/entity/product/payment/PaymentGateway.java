package vn.com.fortis.domain.entity.product.payment;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum PaymentGateway {
    @JsonProperty("vnpay")
    VNPAY,
    
    @JsonProperty("momo")
    MOMO;

}
