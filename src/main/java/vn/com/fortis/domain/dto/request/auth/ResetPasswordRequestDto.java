package vn.com.fortis.domain.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.com.fortis.constant.ErrorMessage;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetPasswordRequestDto {

    @Schema(description = "Email", example = "quanducbui2017@gmail.com")
    @NotBlank(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    @Email
    String email;

    @Schema(description = "New pasword", example = "User123@")
    @NotBlank(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_])[A-Za-z\\d\\W_]{8,}$",
            message = ErrorMessage.INVALID_FORMAT_PASSWORD
    )
    String newPassword;
}
