package vn.com.fortis.repository;

import vn.com.fortis.domain.entity.product.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, String> {
}
