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
public class LoginRequestDto {

    @Schema(description = "Email/Username", example = "quanducbui2017@gmail.com")
    @NotBlank(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    @Email()
    String emailOrUsername;

    @Schema(description = "Password", example = "User123@")
    @NotBlank(message = ErrorMessage.INVALID_BLANK_FIELD)
    String password;
}
