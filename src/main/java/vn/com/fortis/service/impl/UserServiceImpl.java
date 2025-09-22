package vn.com.fortis.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import vn.com.fortis.constant.CommonConstant;
import vn.com.fortis.constant.ErrorMessage;
import vn.com.fortis.domain.dto.request.user.ConfirmPasswordUpdateUserRequestDto;
import vn.com.fortis.domain.dto.request.user.UpdatePasswordRequestDto;
import vn.com.fortis.domain.dto.request.user.UpdateUserRequestDto;
import vn.com.fortis.domain.dto.response.user.UserResponseDto;
import vn.com.fortis.domain.entity.user.User;
import vn.com.fortis.domain.mapper.UserMapper;
import vn.com.fortis.exception.InvalidDataException;
import vn.com.fortis.exception.ResourceNotFoundException;
import vn.com.fortis.exception.UploadFileException;
import vn.com.fortis.helper.PersonalInformationHelper;
import vn.com.fortis.repository.UserRepository;
import vn.com.fortis.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Map;


@Service
@Slf4j(topic = "USER-SERVICE")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    UserMapper userMapper;

    PersonalInformationHelper personalInformationHelper;

    Cloudinary cloudinary;

    @Override
    public void deleteAccount(Authentication authentication) {

        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException(ErrorMessage.User.ERR_USER_NOT_EXISTED));

        if (Boolean.TRUE.equals(user.getIsDeleted())) {
            throw new InvalidDataException(ErrorMessage.User.ERR_ACCOUNT_ALREADY_DELETED);
        }

        user.setIsDeleted(CommonConstant.TRUE);
        user.setDeletedAt(new Date());

        userRepository.save(user);
    }

    @Override
    public UserResponseDto getDetailProfile(Authentication authentication) {
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException(ErrorMessage.User.ERR_USER_NOT_EXISTED));

        return userMapper.userToUserResponseDto(user);
    }

    @Override
    public UserResponseDto updateDetailProfile(ConfirmPasswordUpdateUserRequestDto requestDto, Authentication authentication) {
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException(ErrorMessage.User.ERR_USER_NOT_EXISTED));

        if (requestDto.getProfileData() != null) {

            UpdateUserRequestDto personalInfo = personalInformationHelper
                    .handleEmptyStrings(requestDto.getProfileData());

            userMapper.updateUserFromPersonalInformationDto(personalInfo, user);

        }

        User updatedUser = userRepository.save(user);

        return userMapper.userToUserResponseDto(updatedUser);
    }

    @Override
    public void updatePassword(UpdatePasswordRequestDto updatePasswordRequestDto, Authentication authentication) {

        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException(ErrorMessage.User.ERR_USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        if(!passwordEncoder.matches(updatePasswordRequestDto.getCurrentPassword(), user.getPassword()))
            throw new InvalidDataException(ErrorMessage.User.ERR_INCORRECT_PASSWORD);

        if(updatePasswordRequestDto.getNewPassword().equals(updatePasswordRequestDto.getCurrentPassword()))
            throw new InvalidDataException(ErrorMessage.User.ERR_DUPLICATE_OLD_PASSWORD);

        user.setPassword(passwordEncoder.encode(updatePasswordRequestDto.getNewPassword()));
        userRepository.save(user);

    }

    @Override
    public UserResponseDto uploadAvatar(MultipartFile file, Authentication authentication) throws IOException {

        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(
                () -> new ResourceNotFoundException(ErrorMessage.User.ERR_USER_NOT_EXISTED));

        if(user.getAvatarPublicId() != null){
            cloudinary.uploader().destroy(user.getAvatarPublicId(), ObjectUtils.emptyMap());
        }
        String imageUrl;
        String publicId;
        try{
            Map<String, Object> uploadParams = ObjectUtils.asMap(
                    "folder", "haus/avatars",
                    "public_id", "avatar_" + user.getId() + "_" + System.currentTimeMillis(),
                    "resource_type", "image",
                    "overwrite", true,
                    "transformation", "w_400,h_400,c_fill,q_auto"
            );

            Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), uploadParams);
            publicId = (String) result.get("public_id");
            imageUrl = (String) result.get("secure_url");
        } catch (IOException e) {
            throw new UploadFileException(ErrorMessage.User.UPLOAD_AVATAR_FAIL, e);
        }
        user.setAvatarLink(imageUrl);
        user.setAvatarPublicId(publicId);

        userRepository.save(user);
        return userMapper.userToUserResponseDto(user);
    }
}
