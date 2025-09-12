package vn.com.fortis.domain.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.com.fortis.constant.ErrorMessage;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogoutRequestDto {

    @NotBlank(message = ErrorMessage.INVALID_BLANK_FIELD)
    String token;

}
