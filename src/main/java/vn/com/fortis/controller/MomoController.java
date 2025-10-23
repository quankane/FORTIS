package vn.com.fortis.controller;

import vn.com.fortis.base.ResponseUtil;
import vn.com.fortis.base.RestApiV1;
import vn.com.fortis.constant.ErrorMessage;
import vn.com.fortis.constant.SuccessMessage;
import vn.com.fortis.constant.UrlConstant;
import vn.com.fortis.domain.dto.request.product.momo.MomoIpnRequestDto;
import vn.com.fortis.service.MomoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@RestApiV1
@Validated
@RequiredArgsConstructor
@Slf4j(topic = "MOMO-CONTROLLER")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "payment-controller", description = "Payment APIs")
public class MomoController {

    MomoService momoService;

    @Operation(
            summary = "Tạo đơn hàng thanh toán MoMo",
            description = "Khởi tạo giao dịch thanh toán với MoMo và trả về payUrl để điều hướng khách hàng",
            security = @SecurityRequirement(name = "Bearer Token")
    )
    @PostMapping(UrlConstant.Payment.MOMO_CREATE_ORDER)
    public ResponseEntity<?> createMomoOrder(@RequestParam Long orderId) throws JsonProcessingException {

        Map<String, String> result = momoService.createPaymentOrder(orderId);
        String resultCode = result.get("resultCode");
        System.out.println(resultCode);
        boolean success = "0".equals(resultCode);

        return success
                ? ResponseUtil.success(
                HttpStatus.OK,
                SuccessMessage.Payment.CREATE_MOMO_ORDER_SUCCESS,
                result)
                : ResponseUtil.error(
                HttpStatus.BAD_REQUEST,
                ErrorMessage.Payment.CREATE_MOMO_ORDER_FAIL + ": " + result.get("message"));
    }

    @Operation(
            summary = "MoMo IPN (Instant Payment Notification)",
            description = "API này nhận thông báo thanh toán trực tiếp từ MoMo server (Server-to-Server)"
    )
    @PostMapping(UrlConstant.Payment.MOMO_IPN)
    public ResponseEntity<?> momoIPN(@RequestBody MomoIpnRequestDto request) {

        boolean success = momoService.handleIpnCallback(request);

        return success
                ? ResponseUtil.success(
                HttpStatus.OK,
                SuccessMessage.Payment.MOMO_IPN_SUCCESS)
                :ResponseUtil.error(
                HttpStatus.BAD_REQUEST,
                ErrorMessage.Payment.MOMO_IPN_VERIFY_FAIL);
    }


    @Operation(
            summary = "Xử lý MoMo return callback",
            description = "MoMo gọi về khi thanh toán xong (Redirect URL từ Browser)"
    )
    @GetMapping(UrlConstant.Payment.MOMO_CALLBACK)
    public ResponseEntity<?> momoReturn(@RequestParam Map<String, String> allParams) {
        
        Map<String, String> result = momoService.handleRedirectCallback(allParams);
        boolean success = "success".equals(result.get("status"));

        return success
                ? ResponseUtil.success(
                HttpStatus.OK,
                SuccessMessage.Payment.MOMO_CALLBACK_SUCCESS,
                result)
                : ResponseUtil.error(
                HttpStatus.BAD_REQUEST,
                ErrorMessage.Payment.MOMO_CALLBACK_FAIL);

    }

}
