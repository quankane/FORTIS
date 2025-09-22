package vn.com.fortis.domain.dto.response.product;

import vn.com.fortis.constant.MediaType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MediaResponseDto {

    Long id;

    String url;

    MediaType type;
}
