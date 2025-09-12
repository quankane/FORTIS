package vn.com.fortis.controller;

import vn.com.fortis.base.ResponseUtil;
import vn.com.fortis.base.RestApiV1;
import vn.com.fortis.constant.SuccessMessage;
import vn.com.fortis.constant.UrlConstant;
import vn.com.fortis.domain.dto.request.auth.*;
import vn.com.fortis.domain.dto.request.auth.otp.VerifyOtpRequestDto;
import vn.com.fortis.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestApiV1
@Validated
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthenticationService authenticationService;

    @Operation(
            summary = "Login account",
            description = "Used to login account"
    )
    @PostMapping(UrlConstant.Auth.LOGIN)
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return ResponseUtil.success(
                SuccessMessage.Auth.LOGIN_SUCCESS,
                authenticationService.authentication(loginRequestDto)
        );
    }

    @Operation(
            summary = "Logout account",
            description = "Used to logout account"
    )
    @PostMapping(UrlConstant.Auth.LOGOUT)
    public ResponseEntity<?> logout(@Valid @RequestBody LogoutRequestDto logoutRequestDto) {
        authenticationService.logout(logoutRequestDto);
        return ResponseUtil.success(HttpStatus.NO_CONTENT, SuccessMessage.Auth.LOGOUT_SUCCESS, null);
    }

    @Operation(
            summary = "Refresh token",
            description = "Used to reissue token"
    )
    @PostMapping(UrlConstant.Auth.REFRESH_TOKEN)
    public ResponseEntity<?> refresh(@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        return ResponseUtil.success(
                SuccessMessage.Auth.REFRESH_TOKEN_SUCCESS,
                authenticationService.refresh(refreshTokenRequestDto)
        );
    }

    @Operation(
            summary = "Register account",
            description = "Used to register account"
    )
    @PostMapping(UrlConstant.Auth.REGISTER)
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        authenticationService.register(registerRequestDto);
        return ResponseUtil.success(HttpStatus.CREATED, SuccessMessage.Auth.REGISTER_SEND_OTP_SUCCESS, null);
    }

    @Operation(
            summary = "Verify OTP after registration",
            description = "Used to verify OTP after require registry account"
    )
    @PostMapping(UrlConstant.Auth.VERIFY_OTP)
    public ResponseEntity<?> verify(@Valid @RequestBody VerifyOtpRequestDto verifyOtpRequestDto) {
        return ResponseUtil.success(
                HttpStatus.CREATED,
                SuccessMessage.Auth.VERIFY_OTP_REGISTER_SUCCESS,
                authenticationService.verifyOtpToRegister(verifyOtpRequestDto)
        );
    }

    @Operation(
            summary = "Forgot password",
            description = "Used to send email to reset password"
    )
    @PostMapping(UrlConstant.Auth.FORGOT_PASSWORD)
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDto forgotPasswordRequestDto) {
        authenticationService.forgotPassword(forgotPasswordRequestDto);
        return ResponseUtil.success(HttpStatus.ACCEPTED, SuccessMessage.Auth.FORGOT_PASSWORD_SUCCESS, null);
    }

    @Operation(
            summary = "Verify OTP after forgot password",
            description = "Used to verify OTP after require reset password"
    )
    @PostMapping(UrlConstant.Auth.VERIFY_OTP_TO_RESET_PASSWORD)
    public ResponseEntity<?> verifyToResetPassword(@Valid @RequestBody VerifyOtpRequestDto request) {
        return ResponseUtil.success(
                HttpStatus.OK,
                authenticationService.verifyOtpToResetPassword(request) ? SuccessMessage.Auth.VERIFY_OTP_TO_RESET_PASSWORD_SUCCESS : null);
    }

    @Operation(
            summary = "Reset password",
            description = "Used to reset password after receive OTP"
    )
    @PostMapping(UrlConstant.Auth.RESET_PASSWORD)
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequestDto request) {
        return ResponseUtil.success(
                SuccessMessage.Auth.RESET_PASSWORD_SUCCESS,
                authenticationService.resetPassword(request)
        );
    }
}
