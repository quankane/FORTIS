package vn.com.fortis.domain.dto.request.product;

import vn.com.fortis.constant.ErrorMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateProductVariationRequestDto {

    @Schema(description = "File ảnh sản phẩm")
    MultipartFile imageFile;

    @Schema(description = "Màu sắc sản phẩm", example = "Đỏ")
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    String color;

    @Schema(description = "Kích thước sản phẩm", example = "L")
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    String size;

    @Schema(description = "Giá biến thể sản phẩm", example = "1600000.0")
    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    @DecimalMin(value = "0.0", inclusive = false, message = ErrorMessage.Product.ERR_PRICE_INVALID)
    Double price;

    @Schema(description = "Số lượng tồn kho của biến thể", example = "20")
    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    @Min(value = 0, message = ErrorMessage.Product.ERR_QUANTITY_INVALID)
    Integer inventoryQuantity;

    @Schema(description = "ID sản phẩm chính", example = "1")
    @NotNull(message = ErrorMessage.Product.ERR_PRODUCT_NOT_EXISTED)
    Long productId;
}
