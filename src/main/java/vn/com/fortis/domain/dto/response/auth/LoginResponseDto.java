package vn.com.fortis.domain.dto.response.auth;

import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.com.fortis.constant.CommonConstant;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class LoginResponseDto {

    String tokenType = CommonConstant.BEARER_TOKEN;

    String userId;

    String accessToken;

    String refreshToken;

//    Boolean isDeleted;
//
//    Boolean canRecovery;
//
//    long dayRecoveryRemaining;

}
