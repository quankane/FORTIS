package vn.com.fortis.domain.mapper;

import vn.com.fortis.domain.dto.request.admin.CreateUserRequestDto;
import vn.com.fortis.domain.dto.request.user.UpdateUserRequestDto;
import vn.com.fortis.domain.dto.response.user.UserResponseDto;
import vn.com.fortis.domain.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface UserMapper {
    UserResponseDto userToUserResponseDto(User user);

    User createUserRequestDtoToUser(CreateUserRequestDto request);

    void updateUserFromDto(UpdateUserRequestDto request, @MappingTarget User user);

    void updateUserFromPersonalInformationDto(UpdateUserRequestDto request, @MappingTarget User user);
}
