package vn.com.fortis.domain.entity.product.payment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum PaymentStatus {
    @JsonProperty("pending")
    PENDING,

    @JsonProperty("cod")
    COD,

    @JsonProperty("completed")
    COMPLETED,

    @JsonProperty("expired")
    EXPIRED,

    @JsonProperty("cancelled")
    CANCELLED,

    @JsonProperty("refunded")
    REFUNDED
}
