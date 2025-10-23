package vn.com.fortis.service.impl;

import vn.com.fortis.constant.ErrorMessage;
import vn.com.fortis.constant.OrderStatus;
import vn.com.fortis.domain.dto.request.product.CodPaymentRequestDto;
import vn.com.fortis.domain.dto.response.product.CodPaymentResponseDto;
import vn.com.fortis.domain.entity.product.Order;
import vn.com.fortis.domain.entity.product.payment.Payment;
import vn.com.fortis.domain.entity.product.payment.PaymentStatus;
import vn.com.fortis.domain.entity.product.payment.PaymentType;
import vn.com.fortis.exception.InvalidDataException;
import vn.com.fortis.exception.ResourceNotFoundException;
import vn.com.fortis.repository.OrderRepository;
import vn.com.fortis.repository.PaymentRepository;
import vn.com.fortis.service.CodPaymentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j(topic = "COD-PAYMENT-SERVICE")
public class CodPaymentServiceImpl implements CodPaymentService {

    OrderRepository orderRepository;
    PaymentRepository paymentRepository;

    @Override
    @Transactional
    public CodPaymentResponseDto processCodPayment(CodPaymentRequestDto request) {

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() ->  new ResourceNotFoundException(ErrorMessage.Order.ERR_ORDER_NOT_EXISTED));

        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new InvalidDataException(ErrorMessage.Payment.COD_ORDER_ALREADY_COMPLETED);
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new InvalidDataException(ErrorMessage.Payment.COD_ORDER_CANCELLED);
        }

        Payment payment = getOrCreateCodPayment(order);

        order.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);


        return CodPaymentResponseDto.builder()
                .orderId(order.getId())
                .orderNumber(order.getOrderNumber())
                .totalAmount(order.getTotalAmount())
                .orderStatus(order.getStatus())
                .paymentStatus(payment.getStatus())
                .paymentId(payment.getId())
                .message(request.getNote())
                .build();
    }


    private Payment getOrCreateCodPayment(Order order) {
        Optional<Payment> existingPaymentOpt = paymentRepository.findByOrderId(
                order.getId());

        if (existingPaymentOpt.isPresent()) {
            Payment payment = existingPaymentOpt.get();

            if (payment.getStatus() == PaymentStatus.PENDING) {
                payment.setType(PaymentType.COD);
                payment.setGateway(null);
                paymentRepository.save(payment);
            }

            if (payment.getStatus() == PaymentStatus.COMPLETED) {
                throw new InvalidDataException(ErrorMessage.Payment.COD_PAYMENT_ALREADY_COMPLETED);
            }

            if (payment.getStatus() == PaymentStatus.CANCELLED ||
                payment.getStatus() == PaymentStatus.EXPIRED) {
                payment.setStatus(PaymentStatus.PENDING);
                return paymentRepository.save(payment);
            }

            return payment;
        }

        return createCodPaymentRecord(order);
    }


    private Payment createCodPaymentRecord(Order order) {
        Payment payment = Payment.builder()
                .amount(order.getTotalAmount())
                .gateway(null)
                .type(PaymentType.COD)
                .status(PaymentStatus.PENDING)
                .order(order)
                .build();

        Payment savedPayment = paymentRepository.save(payment);
        log.info("Created new COD payment record: {} for order: {}", 
                 savedPayment.getId(), order.getId());
        
        return savedPayment;
    }
}
