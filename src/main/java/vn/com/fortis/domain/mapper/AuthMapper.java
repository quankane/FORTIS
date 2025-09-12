package vn.com.fortis.domain.mapper;

import vn.com.fortis.domain.dto.request.auth.RegisterRequestDto;
import vn.com.fortis.domain.dto.response.user.UserResponseDto;
import vn.com.fortis.domain.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface AuthMapper {

    @Mapping(target = "password", ignore = true)
    User registerRequestDtoToUser(RegisterRequestDto request);

    UserResponseDto userToUserResponseDto(User user);
}
