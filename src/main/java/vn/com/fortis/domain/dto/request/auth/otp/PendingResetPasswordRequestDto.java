package vn.com.fortis.domain.dto.request.auth.otp;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.com.fortis.domain.dto.request.auth.ForgotPasswordRequestDto;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PendingResetPasswordRequestDto {

    ForgotPasswordRequestDto request;
    String otp;
    LocalDateTime expireAt;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireAt);
    }
}