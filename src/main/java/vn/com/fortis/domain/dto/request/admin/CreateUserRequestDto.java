package vn.com.fortis.domain.dto.request.admin;

import vn.com.fortis.constant.ErrorMessage;
import vn.com.fortis.constant.UserType;
import vn.com.fortis.domain.entity.user.Role;
import vn.com.fortis.domain.validator.EnumValue;
import vn.com.fortis.domain.validator.PhoneNumber;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class CreateUserRequestDto {
    @Schema(description = "Tên đăng nhập", example = "user123")
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    String username;

    @Schema(description = "Email người dùng", example = "user@gmail.com")
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    @Email(message = ErrorMessage.INVALID_SOME_THING_FIELD)
    String email;

    @Schema(description = "Mật khẩu", example = "User123@")
    @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=\\S+$).{8,}$", message = ErrorMessage.INVALID_FORMAT_PASSWORD)
    String password;

    @Schema(description = "Họ/Tên đệm", example = "Phạm Văn")
    String firstName;

    @Schema(description = "Tên", example = "A")
    String lastName;

    @Schema(description = "Ngày sinh", example = "2000-01-01")
    @NotNull(message = "dateOfBirth must be not null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    Date dateOfBirth;

    @Schema(description = "Số điện thoại", example = "0123456789")
    @PhoneNumber()
    String phone;

    @Schema(description = "Quốc tịch", example = "Việt Nam")
    String nationality;

    @Schema(description = "Vai trò", example = "USER")
    @EnumValue(name = "type", enumClass = UserType.class)
    Role role;
}
