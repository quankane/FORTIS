    package vn.com.fortis.domain.dto.response.cart;

    import lombok.*;
    import lombok.experimental.FieldDefaults;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public class CartItemResponse {

        Long productVariationId;

        Integer quantity;

    }
