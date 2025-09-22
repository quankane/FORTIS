package vn.com.fortis.service;

import vn.com.fortis.domain.dto.request.user.ConfirmPasswordUpdateUserRequestDto;
import vn.com.fortis.domain.dto.request.user.UpdatePasswordRequestDto;
import vn.com.fortis.domain.dto.response.user.UserResponseDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

    void deleteAccount(Authentication authentication);

    UserResponseDto getDetailProfile(Authentication authentication);

    UserResponseDto updateDetailProfile(ConfirmPasswordUpdateUserRequestDto requestDto, Authentication authentication);

    void updatePassword(UpdatePasswordRequestDto updatePasswordRequestDto, Authentication authentication);

    UserResponseDto uploadAvatar(MultipartFile file, Authentication authentication) throws IOException;
}
