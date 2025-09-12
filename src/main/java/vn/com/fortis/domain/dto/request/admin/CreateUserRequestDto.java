package vn.com.fortis.domain.dto.request.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import vn.com.fortis.constant.ErrorMessage;
import vn.com.fortis.constant.UserType;
import vn.com.fortis.domain.entity.user.Role;
import vn.com.fortis.domain.validator.Email;
import vn.com.fortis.domain.validator.EnumValue;
import vn.com.fortis.domain.validator.PhoneNumber;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserRequestDto {
    @Schema(description = "User name", example = "quanducbui2017@gmail.com")
    @NotBlank(message = ErrorMessage.INVALID_BLANK_FIELD)
    String username;

    @Schema(description = "Email", example = "quanducbui2017@gmail.com")
    @NotBlank(message = ErrorMessage.INVALID_BLANK_FIELD)
    @Email(message = ErrorMessage.INVALID_SOME_THING_FIELD)
    String email;

    @Schema(description = "Password", example = "User123@")
    @NotBlank(message = ErrorMessage.INVALID_BLANK_FIELD)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=\\S+$).{8,}$", message = ErrorMessage.INVALID_FORMAT_PASSWORD)
    String password;

    @Schema(description = "First name", example = "Quân")
    String firstName;

    @Schema(description = "Last name", example = "Bùi")
    String lastName;

    @Schema(description = "Date of birth", example = "19/05/2005")
    @NotNull(message = ErrorMessage.User.ERR_DATE_OF_BIRTH_NULL)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    Date dateOfBirth;

    @Schema(description = "Phone number", example = "0123456789")
    @PhoneNumber()
    String phone;

    @Schema(description = "National", example = "Việt Nam")
    String nationality;

    @Schema(description = "Role", example = "USER")
    @EnumValue(name = "type", enumClass = UserType.class)
    Role role;
}
