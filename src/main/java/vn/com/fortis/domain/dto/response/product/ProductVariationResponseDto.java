package vn.com.fortis.domain.dto.response.product;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariationResponseDto {

    Long id;

    String color;

    String size;

    Double price;

    Integer inventoryQuantity;

    MediaResponseDto media;

}
