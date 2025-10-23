package vn.com.fortis.service;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface PaymentService {
    public String createVNPayUrl(Long orderId, HttpServletRequest request);

    public boolean checkVNPayCallback(Map<String, String> params);
}
