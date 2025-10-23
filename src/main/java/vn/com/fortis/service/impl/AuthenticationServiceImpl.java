package vn.com.fortis.service.impl;

import vn.com.fortis.constant.CommonConstant;
import vn.com.fortis.constant.ErrorMessage;
import vn.com.fortis.constant.TokenType;
import vn.com.fortis.domain.entity.InvalidatedToken;
import vn.com.fortis.domain.entity.user.Role;
import vn.com.fortis.domain.entity.user.User;
import vn.com.fortis.domain.mapper.AuthMapper;
import vn.com.fortis.domain.dto.request.auth.*;
import vn.com.fortis.domain.dto.request.auth.otp.PendingRegistrationRequestDto;
import vn.com.fortis.domain.dto.request.auth.otp.PendingResetPasswordRequestDto;
import vn.com.fortis.domain.dto.request.auth.otp.VerifyOtpRequestDto;
import vn.com.fortis.domain.dto.response.auth.LoginResponseDto;
import vn.com.fortis.domain.dto.response.auth.RefreshTokenResponseDto;
import vn.com.fortis.domain.dto.response.user.UserResponseDto;
import vn.com.fortis.exception.InvalidDataException;
import vn.com.fortis.exception.ResourceNotFoundException;
import vn.com.fortis.repository.InvalidatedTokenRepository;
import vn.com.fortis.repository.UserRepository;
import vn.com.fortis.security.CustomUserDetailsService;
import vn.com.fortis.service.AuthenticationService;
import vn.com.fortis.service.EmailService;
import vn.com.fortis.service.JwtService;
import vn.com.fortis.service.UserService;
import vn.com.fortis.utils.OtpUtils;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.com.fortis.utils.OtpUtils;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j(topic = "AUTHENTICATION-SERVICE")
public class AuthenticationServiceImpl implements AuthenticationService {

    JwtService jwtService;

    AuthMapper authMapper;

    CustomUserDetailsService userDetailsService;

    EmailService emailService;

    AuthenticationManager authenticationManager;

    UserService userService;

    InvalidatedTokenRepository invalidatedTokenRepository;

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    Map<String, PendingRegistrationRequestDto> pendingRegisterMap = new ConcurrentHashMap<>();

    Map<String, PendingResetPasswordRequestDto> pendingResetPasswordMap = new ConcurrentHashMap<>();

    @Override
    public LoginResponseDto authentication(LoginRequestDto request) {

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new ResourceNotFoundException(ErrorMessage.User.ERR_USER_NOT_EXISTED));

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(),
                            request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (DisabledException e) {
            throw new BadCredentialsException(ErrorMessage.Auth.ERR_ACCOUNT_LOCKED);
        } catch (AuthenticationException e) {
            throw new InternalAuthenticationServiceException(ErrorMessage.Auth.ERR_INCORRECT_PASSWORD);
        }
        String accessToken = jwtService.generateAccessToken(user.getId(), user.getUsername(),
                List.of(new SimpleGrantedAuthority(user.getRole().toString())));

        String refreshToken = jwtService.generateRefreshToken(user.getId(), user.getUsername(),
                List.of(new SimpleGrantedAuthority(user.getRole().toString())));

        //save to redis if use (Best practise) -> Although you can use both redis and db

        return LoginResponseDto.builder()
                .tokenType(CommonConstant.BEARER_TOKEN)
                .userId(user.getId())
                .role(user.getRole().toString())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void logout(LogoutRequestDto request) {
        String jwtId = null;
        Date expirationTime = null;
        try {
            SignedJWT signedJwt = SignedJWT.parse(request.getToken());

            jwtId = signedJwt.getJWTClaimsSet().getJWTID();
            expirationTime = signedJwt.getJWTClaimsSet().getExpirationTime();

            invalidatedTokenRepository.save(new InvalidatedToken(jwtId, expirationTime));

        } catch (ParseException ex) {
            log.error("Signed Jwt parsed fail, message = {}", ex.getMessage());
            throw new InvalidDataException(ErrorMessage.Auth.ERR_TOKEN_INVALIDATED);
        }
    }

    @Override
    public RefreshTokenResponseDto refresh(RefreshTokenRequestDto request) {
        String refreshToken = request.getRefreshToken();

        String username = jwtService.extractUserName(refreshToken, TokenType.REFRESH_TOKEN);

        if (jwtService.isExpired(refreshToken, TokenType.REFRESH_TOKEN)) {
            throw new InvalidDataException(ErrorMessage.Auth.EXPIRED_REFRESH_TOKEN);
        }

        if (!jwtService.isValid(refreshToken, TokenType.REFRESH_TOKEN, username)) {
            throw new InvalidDataException(ErrorMessage.Auth.INVALID_REFRESH_TOKEN);
        }

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(ErrorMessage.User.ERR_USER_NOT_EXISTED));

        String accessToken = jwtService.generateAccessToken(user.getId(), user.getUsername(),
                List.of(new SimpleGrantedAuthority(user.getRole().toString())));

        return RefreshTokenResponseDto.builder()
                .tokenType(CommonConstant.BEARER_TOKEN)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void register(RegisterRequestDto request) {
        if (userRepository.existsUserByUsername(request.getUsername()))
            throw new InvalidDataException(ErrorMessage.User.ERR_USERNAME_EXISTED);

        if (userRepository.existsUserByEmail(request.getEmail()))
            throw new InvalidDataException(ErrorMessage.User.ERR_EMAIL_EXISTED);

        String otp = OtpUtils.generateOtp();

        PendingRegistrationRequestDto pending = new PendingRegistrationRequestDto();

        pending.setRequest(request);
        pending.setOtp(otp);
        pending.setExpireAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")).plusMinutes(5));

        pendingRegisterMap.put(request.getEmail(), pending);

        emailService.sendRegistrationOtpByEmail(request.getEmail(), request.getUsername(), otp);
    }

    @Override
    public UserResponseDto verifyOtpToRegister(VerifyOtpRequestDto request) {
        PendingRegistrationRequestDto pending = pendingRegisterMap.get(request.getEmail());

        if (pending == null){
            throw new InvalidDataException(ErrorMessage.Auth.ERR_PENDING_REGISTER_REQUEST_NULL);
        }

        if (pending.isExpired())
            throw new InvalidDataException(ErrorMessage.Auth.ERR_OTP_EXPIRED);

        if (!pending.getOtp().equals(request.getOtp()))
            throw new InvalidDataException(ErrorMessage.Auth.ERR_OTP_NOT_MATCH);

        RegisterRequestDto req = pending.getRequest();

        User user = authMapper.registerRequestDtoToUser(req);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);

        pendingRegisterMap.remove(request.getEmail());

        return authMapper.userToUserResponseDto(user);
    }

    @Override
    public void forgotPassword(ForgotPasswordRequestDto request) {
        log.info(request.getEmail());

        if (!userRepository.existsUserByEmail(request.getEmail()))
            throw new ResourceNotFoundException(ErrorMessage.User.ERR_EMAIL_NOT_EXISTED);

        String otp = OtpUtils.generateOtp();

        PendingResetPasswordRequestDto pending = new PendingResetPasswordRequestDto();

        pending.setRequest(request);
        pending.setOtp(otp);
        pending.setExpireAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")).plusMinutes(5));

        pendingResetPasswordMap.put(request.getEmail(), pending);

        emailService.sendForgotPasswordOtpByEmail(request.getEmail(), request.getEmail(), otp);
    }

    @Override
    public boolean verifyOtpToResetPassword(VerifyOtpRequestDto request) {
        PendingResetPasswordRequestDto pending = pendingResetPasswordMap.get(request.getEmail());

        if (pending == null)
            throw new InvalidDataException(ErrorMessage.Auth.ERR_PENDING_RESET_REQUEST_NULL);

        if (pending.isExpired())
            throw new InvalidDataException(ErrorMessage.Auth.ERR_OTP_EXPIRED);

        if (!pending.getOtp().equals(request.getOtp()))
            throw new InvalidDataException(ErrorMessage.Auth.ERR_OTP_NOT_MATCH);

        return pendingResetPasswordMap.containsKey(request.getEmail())
                && pendingResetPasswordMap.get(request.getEmail()).getOtp().equals(request.getOtp());
    }

    @Override
    public UserResponseDto resetPassword(ResetPasswordRequestDto request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.User.ERR_USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword()))
            throw new InvalidDataException(ErrorMessage.User.ERR_DUPLICATE_OLD_PASSWORD);

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

        pendingResetPasswordMap.remove(request.getEmail());

        return authMapper.userToUserResponseDto(user);
    }

}
