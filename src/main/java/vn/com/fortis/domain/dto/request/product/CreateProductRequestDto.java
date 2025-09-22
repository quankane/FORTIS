package vn.com.fortis.domain.dto.request.product;

import vn.com.fortis.constant.ErrorMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateProductRequestDto {

    @Schema(description = "Tên sản phẩm", example = "Ghế sofa phòng khách")
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    String productName;

    @Schema(description = "Mô tả sản phẩm", example = "Ghế sofa cao cấp, chất liệu da thật, thiết kế hiện đại")
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    String description;

    @Schema(description = "Mô tả chi tiết sản phẩm",
            example = """
                    Ghế có tay vịn BONDHOLMEN từ IKEA mang đến sự kết hợp hoàn hảo giữa phong cách hiện đại và sự thoải mái tối ưu cho không gian ngoài trời của bạn.               
                    Với thiết kế độc đáo, khung nhôm chắc chắn được sơn tĩnh điện màu be trang nhã và mặt ngồi, lưng tựa được đan bằng các sợi polyester bền bỉ.                   
                    Chiều rộng: 61 cm                   
                    Chiều sâu: 69 cm                    
                    Chiều cao: 81 cm
                    Chiều rộng mặt ngồi: 48 cm
                    Chiều sâu mặt ngồi: 50 cm
                    Chiều cao mặt ngồi: 42 cm
                    Chiều cao tay vịn: 63 cm
                    """
    )
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    String detailDescription;

    @NotNull(message = ErrorMessage.NOT_NULL_FIELD)
    @Min(value = 0, message = ErrorMessage.Product.ERR_PRICE_INVALID)
    Double price;

    @NotNull(message = ErrorMessage.NOT_NULL_FIELD)
    @Schema(description = "Category Id", example = "1")
    Long categoryId;

    @NotNull(message = ErrorMessage.NOT_NULL_FIELD)
    @Schema(description = "Inventory quantity", example = "500")
            @Min(value = 1, message = ErrorMessage.Product.ERR_PRODUCT_INVENTORY_QUANTITY_LESS_THAN_1)
    Integer inventoryQuantity;

}
