package vn.com.fortis.controller;


import vn.com.fortis.base.ResponseUtil;
import vn.com.fortis.base.RestApiV1;
import vn.com.fortis.constant.ErrorMessage;
import vn.com.fortis.constant.SuccessMessage;
import vn.com.fortis.constant.UrlConstant;
import vn.com.fortis.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@RestApiV1
@Validated
@RequiredArgsConstructor
@Slf4j(topic = "PAYMENT-CONTROLLER")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "payment-controller", description = "Payment APIs")
public class PaymentController {

    PaymentService paymentService;

    @Operation(
            summary = "Lấy VNPay URL",
            description = "Tạo VNPay URL cho thanh toán VNPay với orderId",
            security = @SecurityRequirement(name = "Bearer Token")
    )
    @GetMapping(UrlConstant.Payment.GET_PAYMENT_URL)
    public ResponseEntity<?> getVNPayUrl(
            @RequestParam(value = "orderId") Long orderId,
            HttpServletRequest request
    ) {
            String VNPayUrl = paymentService.createVNPayUrl(orderId, request);
            return ResponseUtil.success(
                            HttpStatus.OK,
                            SuccessMessage.Payment.GET_VNPAYURL_SUCCESS,
                            VNPayUrl);

    }

    @Operation(
        summary = "VNPay IPN (Instant Payment Notification)",
        description = "API này nhận thông báo thanh toán trực tiếp từ VNPay server (Trước vnpay return api) (Server-to-Server)"
    )
    @GetMapping(UrlConstant.Payment.VNPAY_IPN)
    public ResponseEntity<?> vnPayIPN(@RequestParam Map<String, String> allParams) {

            boolean success = paymentService.checkVNPayCallback(allParams);
            Map<String, String> response = new HashMap<>();

            if (success) {
                    response.put("RspCode", "00");
                    response.put("Message", "Confirm Success");
            } else {
                    response.put("RspCode", "99");
                    response.put("Message", "Confirm Fail");
            }

            return ResponseUtil.success(HttpStatus.OK, "" , response);
    }
    

    @Operation(
            summary = "Xử lý VNPay return",
            description = "VNPay gọi về khi thanh toán xong (Callback URL)"
    )
    @GetMapping(UrlConstant.Payment.VNPAY_RETURN)
    public ResponseEntity<?> vnPayReturn(@RequestParam Map<String, String> allParams) {
        boolean success = paymentService.checkVNPayCallback(allParams);

        return success
                ? ResponseUtil.success(HttpStatus.OK, SuccessMessage.Payment.CALLBACK_VNPAY_SUCCESS)
                : ResponseUtil.error(HttpStatus.BAD_REQUEST, ErrorMessage.Payment.CALLBACK_VNPAY_FAIL);
    }

    

}
