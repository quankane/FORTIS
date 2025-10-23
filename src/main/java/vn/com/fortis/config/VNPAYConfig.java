package vn.com.fortis.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Getter
public class VNPAYConfig {
    //VNPAY_URL=https://sandbox.vnpayment.vn/paymentv2/vpcpay.html
    //VNPAY_RETURN_URL=https://socko-stratagemical-abdullah.ngrok-free.dev/api/v1/payment/vnpay-return
    //VNPAY_TMN_CODE=8X9ZJV1I #VNP CUNG CẤP
    //VNPAY_HASH_SECRET=5XB2H37CCYP3UEC4DNAPO8P7WA62G148 #VNP CUNG CẤP
    //VNPAY_VERSION=2.1.0
    //VNPAY_COMMAND=pay
    //VNPAY_MAXTIME=900
    //VNPAY_ORDER_TYPE=other
    //VNPAY_CURRCODE=VND
    //VNPAY_LOCALE=vn
    @Value("${payment.vnpayUrl}")
    private String vnp_Url;

    @Value("${payment.vnpayReturnUrl}")
    private String vnp_ReturnUrl;

    @Value("${payment.vnpayTmnCode}")
    private String vnp_TmnCode;

    @Value("${payment.vnpayHashSecret}")
    private String vnp_HashSecret;

    @Value("${payment.vnpVersion}")
    private String vnp_Version;

    @Value("${payment.vnpayCommand}")
    private String vnp_Command;

    @Value("${payment.vnpayOrderType}")
    private String vnp_OrderType;

    @Value("${payment.vnpayCurrCode}")
    private String vnp_CurrCode;

    @Value("${payment.vnpayLocale}")
    private String vnp_Locale;

    public Map<String, String> getConfig() {
        Map<String, String> params = new HashMap<>();
        params.put("vnp_Command", vnp_Command);
        params.put("vnp_CurrCode", vnp_CurrCode);
        params.put("vnp_Locale", vnp_Locale);
        params.put("vnp_OrderType", vnp_OrderType);
        params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        params.put("vnp_TmnCode", vnp_TmnCode);
        params.put("vnp_Version", vnp_Version);
        return params;
    }
}
