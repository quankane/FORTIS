package vn.com.fortis.domain.mapper;

import vn.com.fortis.domain.dto.request.address.AddressRequestDto;
import vn.com.fortis.domain.dto.response.address.AddressResponseDto;
import vn.com.fortis.domain.entity.address.Address;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface AddressMapper {
    @Mapping(target = "userId", source = "user.id")
    AddressResponseDto addressToAddressResponseDto(Address address);

    void updateAddressFromDto(AddressRequestDto addressRequestDto, @MappingTarget Address address);

    Address addressRequestDtoToAddress(AddressRequestDto addressRequestDto);
}
