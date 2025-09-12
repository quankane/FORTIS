package vn.com.fortis.domain.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.com.fortis.constant.ErrorMessage;
import vn.com.fortis.domain.validator.Email;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ForgotPasswordRequestDto {

    @Schema(description = "email", example = "quanducbui2017@gmail.com")
    @NotBlank(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    @Email
    String email;
}
