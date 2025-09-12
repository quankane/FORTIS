package vn.com.fortis.domain.dto.request.auth.otp;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.com.fortis.constant.ErrorMessage;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VerifyOtpRequestDto {

    @Schema(description = "Email", example = "quanducbui2017@gmail.com")
    @NotBlank(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    String email;

    @Schema(description = "OTP code")
    @NotBlank(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    String otp;
}
