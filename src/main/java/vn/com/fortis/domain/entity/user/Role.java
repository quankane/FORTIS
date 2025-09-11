package vn.com.fortis.domain.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Role {
    @JsonProperty("user")
    USER,
    @JsonProperty("admin")
    ADMIN,
    @JsonProperty("seller")
    SELLER
}
