package vn.com.fortis.service.impl;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.openid.connect.sdk.claims.ClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.com.fortis.constant.CommonConstant;
import vn.com.fortis.constant.ErrorMessage;
import vn.com.fortis.constant.TokenType;
import vn.com.fortis.domain.dto.request.auth.*;
import vn.com.fortis.domain.dto.request.auth.otp.PendingRegistrationRequestDto;
import vn.com.fortis.domain.dto.request.auth.otp.PendingResetPasswordRequestDto;
import vn.com.fortis.domain.dto.request.auth.otp.VerifyOtpRequestDto;
import vn.com.fortis.domain.dto.response.auth.LoginResponseDto;
import vn.com.fortis.domain.dto.response.auth.RefreshTokenResponseDto;
import vn.com.fortis.domain.dto.response.user.UserResponseDto;
import vn.com.fortis.domain.entity.InvalidatedToken;
import vn.com.fortis.domain.entity.user.User;
import vn.com.fortis.domain.mapper.AuthMapper;
import vn.com.fortis.exception.InvalidDataException;
import vn.com.fortis.exception.ResourceNotFoundException;
import vn.com.fortis.repository.InvalidatedTokenRepository;
import vn.com.fortis.repository.UserRepository;
import vn.com.fortis.service.AuthenticationService;
import vn.com.fortis.service.JwtService;
import vn.com.fortis.utils.OtpUtils;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static vn.com.fortis.constant.TokenType.REFRESH_TOKEN;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j(topic = "AUTHENTICATION-SERVICE")
public class AuthenticationServiceImpl implements AuthenticationService {

    JwtService jwtService;

    UserRepository userRepository;

    AuthenticationManager authenticationManager;

    InvalidatedTokenRepository invalidatedTokenRepository;

    AuthMapper authMapper;

    Map<String, PendingRegistrationRequestDto> pendingRegisterMap = new ConcurrentHashMap<>();

    Map<String, PendingResetPasswordRequestDto> pendingResetPasswordMap = new ConcurrentHashMap<>();

    @Override
    public LoginResponseDto authentication(LoginRequestDto request) {
        User user = userRepository.findByEmailOrUsername(request.getEmailOrUsername(), request.getEmailOrUsername())
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorMessage.User.ERR_USER_NOT_EXISTED
                ));

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),  user.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (DisabledException e) {
            throw new BadCredentialsException(ErrorMessage.Auth.ERR_ACCOUNT_LOCKED);
        } catch (AuthenticationException e) {
            throw new InternalAuthenticationServiceException(ErrorMessage.Auth.ERR_INCORRECT_PASSWORD);
        }

        String accessToken = jwtService.generateAccessToken(user.getId(), user.getUsername(), List.of(new SimpleGrantedAuthority(user.getRole().name())));
        String refreshToken = jwtService.generateRefreshToken(user.getId(), user.getUsername(), List.of(new SimpleGrantedAuthority(user.getRole().name())));

        return LoginResponseDto.builder()
                .tokenType(CommonConstant.BEARER_TOKEN)
                .userId(user.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void logout(LogoutRequestDto request) {
        String jwtId = null;
        Date jwtExpiration = null;
        try {
            SignedJWT signedJWT = SignedJWT.parse(request.getToken());

            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
            jwtId = claimsSet.getJWTID();
            jwtExpiration = claimsSet.getExpirationTime();

            invalidatedTokenRepository.save(new InvalidatedToken(jwtId, jwtExpiration));
        } catch (ParseException ex) {
            log.error("Signed Jwt parsed fail, message = {}", ex.getMessage());
            throw new InvalidDataException(ErrorMessage.Auth.ERR_TOKEN_INVALIDATED);
        }
    }

    @Override
    public RefreshTokenResponseDto refresh(RefreshTokenRequestDto request) {

        String refreshToken = request.getRefreshToken();

        String username = jwtService.extractUserName(refreshToken, REFRESH_TOKEN);

        User user = userRepository.findByUsername(username).orElseThrow(()
                -> new ResourceNotFoundException(ErrorMessage.User.ERR_USER_NOT_EXISTED));

        if(jwtService.isExpired(refreshToken, REFRESH_TOKEN)) {
            throw new InvalidDataException(ErrorMessage.Auth.EXPIRED_REFRESH_TOKEN);
        }
        if(!jwtService.isValid(refreshToken, REFRESH_TOKEN, user.getUsername())) {
            throw new InvalidDataException(ErrorMessage.Auth.INVALID_REFRESH_TOKEN);
        }

        String accessToken = jwtService.generateAccessToken(user.getId(), user.getUsername(), List.of(new SimpleGrantedAuthority(user.getRole().name())));

        return RefreshTokenResponseDto.builder()
                .tokenType(CommonConstant.BEARER_TOKEN)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void register(RegisterRequestDto request) {
        if (userRepository.existsUserByEmail(request.getEmail())) {
            throw new InvalidDataException(ErrorMessage.User.ERR_EMAIL_EXISTED);
        }
        if (userRepository.existsUserByUsername(request.getUsername())) {
            throw new InvalidDataException(ErrorMessage.User.ERR_USERNAME_EXISTED);
        }

        String otp = OtpUtils.generateOtp();

        PendingRegistrationRequestDto pending = new PendingRegistrationRequestDto();

        pending.setRequest(request);
        pending.setOtp(otp);
        pending.setExpireAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")).plusMinutes(5));

        pendingRegisterMap.put(request.getEmail(), pending);

    }

    @Override
    public UserResponseDto verifyOtpToRegister(VerifyOtpRequestDto request) {
        return null;
    }

    @Override
    public void forgotPassword(ForgotPasswordRequestDto request) {

    }

    @Override
    public boolean verifyOtpToResetPassword(VerifyOtpRequestDto request) {
        return false;
    }

    @Override
    public UserResponseDto resetPassword(ResetPasswordRequestDto request) {
        return null;
    }
}
