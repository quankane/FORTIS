package vn.com.fortis.domain.mapper;

import vn.com.fortis.domain.dto.request.user.UpdateAddressRequestDto;
import vn.com.fortis.domain.entity.address.Address;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface AddressMapper {
    UpdateAddressRequestDto addressUpdateAddressRequestDto(Address address);

    Address updateAddressRequestDtoToAddress(UpdateAddressRequestDto UpdateAddressRequestDto);

}
