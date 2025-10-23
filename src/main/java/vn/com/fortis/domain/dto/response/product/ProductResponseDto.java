package vn.com.fortis.domain.dto.response.product;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponseDto {

    Long id;

    String productCode;

    String productName;

    Double price;

    String description;

    String detailDescription;

    Integer inventoryQuantity;

    Integer soldQuantity;

    Float discountPercent;

    Long daysRemaining;

    Date createdAt;

    Date updatedAt;

    Boolean isDeleted;

    List<MediaResponseDto> medias;

    List<String> categoriesName = new ArrayList<>();

    List<ProductVariationResponseDto> productVariations = new ArrayList<>();
}
