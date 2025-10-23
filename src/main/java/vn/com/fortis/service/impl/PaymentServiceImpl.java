package vn.com.fortis.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.com.fortis.config.VNPAYConfig;
import vn.com.fortis.constant.ErrorMessage;
import vn.com.fortis.constant.OrderStatus;
import vn.com.fortis.domain.entity.product.Order;
import vn.com.fortis.domain.entity.product.payment.Payment;
import vn.com.fortis.domain.entity.product.payment.PaymentStatus;
import vn.com.fortis.domain.entity.product.payment.PaymentType;
import vn.com.fortis.exception.ResourceNotFoundException;
import vn.com.fortis.repository.OrderRepository;
import vn.com.fortis.repository.PaymentRepository;
import vn.com.fortis.service.PaymentService;
import vn.com.fortis.utils.VNPAYUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Slf4j(topic = "PAYMENT-SERVICE")
@Service
public class PaymentServiceImpl implements PaymentService {

    private final OrderRepository orderRepository;

    private final VNPAYConfig vnpayConfig;

    private final PaymentRepository paymentRepository;

    @Value("${payment.vnpayMaxTime}")
    private int maxPaymentTime;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    private String SUCCESS_CODE = "00";

    @Override
    public String createVNPayUrl(Long orderId, HttpServletRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.Order.ERR_ORDER_NOT_EXISTED));

        Payment payment = getPayment(order);

        Date currentTime = new Date();
        Date expiredTime = payment.getExpireAt();

        if (currentTime.after(expiredTime)) {
            payment.setStatus(PaymentStatus.EXPIRED);
            order.setPayment(payment);
            orderRepository.save(order);
            paymentRepository.save(payment);
            throw new ResourceNotFoundException(ErrorMessage.Order.ERR_PAYMENT_EXPIRED);
        }

        Map<String, String> params = vnpayConfig.getConfig();
        buildTimeParams(params, expiredTime, currentTime);
        params.put("vnp_Amount", String.valueOf(payment.getAmount() * 100L));

        //Tạo mã giao dịch
        String ref = order.getId() + "-" + System.currentTimeMillis();
        params.put("vnp_txnRef", ref);
        params.put("vnp_OrderInfo", "Payment for order " + order.getId());

        //Lấy IP
        String ipAddr = VNPAYUtils.getIpAddress(request, activeProfile);
        params.put("vnp_IpAddr", ipAddr);

        // Tạo hashData
        String hashData = VNPAYUtils.createPaymentUrl(params);
        String vnpSecureHash = VNPAYUtils.hmacSHA512(vnpayConfig.getVnp_HashSecret(), hashData);

        return vnpayConfig.getVnp_Url() + "?" + hashData + "&vnp_SecureHash=" + vnpSecureHash;
    }

    private void buildTimeParams(Map<String, String> params, Date expiredTime, Date currentTime) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        //Thời gian còn lại
        long diffInMillis = expiredTime.getTime() - currentTime.getTime();
        int remainingSeconds = (int) TimeUnit.MILLISECONDS.toSeconds(diffInMillis);

        //Thời gian tối đa cho phép
        int allowedTime = Math.min(remainingSeconds, maxPaymentTime);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        //Ngày tạo giao dịch
        String vnp_CreateDate = simpleDateFormat.format(calendar.getTime());
        params.put("vnp_CreateDate", vnp_CreateDate);

        //Ng̀y hết hạn giao dịch
        calendar.add(Calendar.SECOND, allowedTime);
        String vnp_ExpiredDate = simpleDateFormat.format(calendar.getTime());
        params.put("vnp_ExpireDate", vnp_ExpiredDate);
    }

    @NotNull
    private static Payment getPayment(Order order) {
        Payment payment = order.getPayment();
        if (payment == null) {
            throw new ResourceNotFoundException(ErrorMessage.Order.ERR_PAYMENT_NOT_FOUND);
        }
        if (payment.getType() != PaymentType.BANK_TRANSFER) {
            throw new ResourceNotFoundException(ErrorMessage.Order.ERR_PAYMENT_TYPE_INVALID);
        }
        if (payment.getStatus() == PaymentStatus.EXPIRED) {
            throw new ResourceNotFoundException(ErrorMessage.Order.ERR_PAYMENT_EXPIRED);
        }
        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new ResourceNotFoundException(ErrorMessage.Order.ERR_PAYMENT_STATUS_INVALID);
        }
        return payment;
    }

    @Override
    public boolean checkVNPayCallback(Map<String, String> params) {
        String vnp_SecureHash = params.get("vnp_SecureHash");

        Map<String, String> fieldsToHash = new HashMap<>(params);
        fieldsToHash.remove("vnp_SecureHash");
        fieldsToHash.remove("vnp_SecureHashType");

        String hashSecret = vnpayConfig.getVnp_HashSecret();
        log.info("Fields to hash: {}", fieldsToHash);

        String signValue = VNPAYUtils.hashAllFields(fieldsToHash, hashSecret);
        log.info("Signatures match: {}", signValue.equals(vnp_SecureHash));

        if (!signValue.equals(vnp_SecureHash)) {
            log.error("Invalid signature! Expected: {}, Got: {}", signValue, vnp_SecureHash);
            return false;
        }

        String txnRef = params.get("vnp_TxnRef");
        String responseCode = params.get("vnp_ResponseCode");
        Long amount = Long.parseLong(params.get("vnp_Amount")) / 100;

        String[] p = txnRef.split("-");
        Order order = orderRepository.findById(Long.valueOf(p[0]))
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.Order.ERR_ORDER_NOT_EXISTED));

        Payment payment = order.getPayment();

        // Verify amount
        if (!payment.getAmount().equals(amount)) {
            payment.setStatus(PaymentStatus.CANCELLED);
            order.setStatus(OrderStatus.CANCELLED);
            paymentRepository.save(payment);
            orderRepository.save(order);
            return false;
        }

        if (SUCCESS_CODE.equals(responseCode)) {
            payment.setStatus(PaymentStatus.COMPLETED);
            order.setStatus(OrderStatus.COMPLETED);
        } else {
            payment.setStatus(PaymentStatus.CANCELLED);
            order.setStatus(OrderStatus.CANCELLED);
        }

        paymentRepository.save(payment);
        orderRepository.save(order);

        return SUCCESS_CODE.equals(responseCode);
    }
}
