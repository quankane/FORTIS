package vn.com.fortis.domain.entity.product.payment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum PaymentType {
    @JsonProperty("cash_on_delivery")
    COD,

    @JsonProperty("bank_transfer")
    BANK_TRANSFER;
}