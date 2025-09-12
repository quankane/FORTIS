package vn.com.fortis.domain.dto.request.auth.otp;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.com.fortis.domain.dto.request.auth.RegisterRequestDto;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PendingRegistrationRequestDto {
    RegisterRequestDto request;
    String otp;
    LocalDateTime expireAt;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireAt);
    }
}
