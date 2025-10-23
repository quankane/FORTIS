package vn.com.fortis.domain.dto.request.address;

import vn.com.fortis.constant.ErrorMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressRequestDto {

    @NotEmpty(message = ErrorMessage.Address.ERR_COUNTRY_EMPTY)
    @Schema(example = "Việt Nam")
    private String country;

    @NotEmpty(message = ErrorMessage.Address.ERR_CITY_EMPTY)
    @Schema(example = "Hà Nội")
    private String city;

    @NotEmpty(message = ErrorMessage.Address.ERR_DISTRICT_EMPTY)
    @Schema(example = "Hoàng Mai")
    private String district;

    @NotEmpty(message = ErrorMessage.Address.ERR_COMMUNE_EMPTY)
    @Schema(example = "Xuân Phương")
    private String commune;

    @NotNull(message = ErrorMessage.Address.ERR_DETAIL_ADDRESS_NULL)
    @Schema(example = "Số nhà 1, ngõ 1, Xuân Phương, Hoàng Mai, Hà Nội")
    private String detailAddress;

}
