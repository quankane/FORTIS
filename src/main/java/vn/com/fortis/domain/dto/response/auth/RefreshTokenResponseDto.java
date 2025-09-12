package vn.com.fortis.domain.dto.response.auth;

import vn.com.fortis.constant.CommonConstant;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshTokenResponseDto {

    String tokenType = CommonConstant.BEARER_TOKEN;

    String accessToken;

    String refreshToken;

}

