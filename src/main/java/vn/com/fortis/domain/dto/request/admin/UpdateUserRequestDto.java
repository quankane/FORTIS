package vn.com.fortis.domain.dto.request.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import vn.com.fortis.domain.entity.user.Role;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequestDto {
    @Schema(description = "Username", example = "username")
    String username;

    @Schema(description = "Password", example = "User123@")
    String password;

    @Schema(description = "First name", example = "Quân")
    String firstName;

    @Schema(description = "Last name", example = "Bùi")
    String lastName;

    @Schema(description = "Date of birth", example = "19/05/2005")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    Date dateOfBirth;

    @Schema(description = "Email", example = "example@gmail.com")
    String email;


    @Schema(description = "National", example = "Việt nam")
    String nationality;

    @Schema(description = "Role", example = "USER")
    Role role;

    @Schema(description = "Is active", example = "true")
    Boolean isActive;
}