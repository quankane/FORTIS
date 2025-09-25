package vn.com.fortis.domain.dto.request.auth;

import vn.com.fortis.constant.ErrorMessage;
import vn.com.fortis.domain.validator.Email;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequestDto {

    @Schema(description = "Email người dùng", example = "quanducbui2017@gmail.com")
    @NotBlank(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    @Email
    String email;

    @Schema(description = "Mật khẩu", example = "Quankane1905@")
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    String password;

}
