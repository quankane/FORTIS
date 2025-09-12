package vn.com.fortis.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import vn.com.fortis.base.ResponseUtil;
import vn.com.fortis.base.RestApiV1;
import vn.com.fortis.constant.SuccessMessage;
import vn.com.fortis.constant.UrlConstant;
import vn.com.fortis.domain.dto.utils.ResponseData;
import vn.com.fortis.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j(topic = "EMAIL-CONTROLLER")
@RestApiV1
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Validated
public class EmailController {

    EmailService emailService;


    @PostMapping(UrlConstant.Email.SEND_REGISTRATION_OTP_BY_EMAIL)
    public ResponseEntity<ResponseData<?>> sendRegistrationOtpByEmail(@RequestParam String to, @RequestParam String name, @RequestParam String otp) {
        emailService.sendRegistrationOtpByEmail(to, name, otp);
        log.info("Send registration otp successful by email");
        return ResponseUtil.success(HttpStatus.OK, SuccessMessage.Email.SEND_REGISTRATION_OTP_SUCCESS);
    }

    @PostMapping(UrlConstant.Email.SEND_FORGOT_PASSWORD_OTP_BY_EMAIL)
    public ResponseEntity<ResponseData<?>> sendForgotPasswordOtpByEmail(@RequestParam String to, @RequestParam String name, @RequestParam String otp) {
        emailService.sendForgotPasswordOtpByEmail(to, name, otp);
        log.info("Send forgot password otp successful by email");
        return ResponseUtil.success(HttpStatus.OK, SuccessMessage.Email.SEND_FORGOT_PASSWORD_OTP_SUCCESS);
    }
}
