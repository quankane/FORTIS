package vn.com.fortis.repository;

import org.springframework.data.repository.query.Param;
import vn.com.fortis.domain.entity.product.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, String> {

    Optional<Payment> findByOrderId(@Param("orderId") Long orderId);
}
