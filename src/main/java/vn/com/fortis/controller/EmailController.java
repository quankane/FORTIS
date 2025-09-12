package vn.com.fortis.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vn.com.fortis.base.ResponseUtil;
import vn.com.fortis.base.RestApiV1;
import vn.com.fortis.constant.SuccessMessage;
import vn.com.fortis.domain.dto.utils.ResponseData;
import vn.com.fortis.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j(topic = "EMAIL-CONTROLLER")
@RequestMapping("/email")
@RestApiV1
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailController {

    EmailService emailService;


    @PostMapping("/send-verification-email")
    public ResponseEntity<ResponseData<?>> sendRegistrationOtpByEmail(@RequestParam String to, @RequestParam String name, @RequestParam String otp) {
        emailService.sendRegistrationOtpByEmail(to, name, otp);
        log.info("Send registration otp successful by email");
        return ResponseUtil.success(HttpStatus.OK, SuccessMessage.Email.SEND_REGISTRATION_OTP_SUCCESS);
    }

    @PostMapping("/verification-account")
    public ResponseEntity<ResponseData<?>> sendVerificationAccount(@RequestParam String to, @RequestParam String name, @RequestParam String otp) {
        emailService.sendForgotPasswordOtpByEmail(to, name, otp);
        log.info("Send forgot password otp successful by email");
        return ResponseUtil.success(HttpStatus.OK, SuccessMessage.Email.SEND_FORGOT_PASSWORD_OTP_SUCCESS);
    }
}
