package vn.com.fortis.domain.dto.response.address;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressResponseDto {

    Long id;
    
    String country;
    
    String city;
    
    String district;
    
    String commune;

    String detailAddress;
    
    String userId;
}
