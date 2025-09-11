package vn.com.fortis.domain.entity.product.payment;

import vn.com.fortis.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name = "payment_methods")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentMethod extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", insertable = false, updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    String id;

    @Column(name = "method_name", nullable = false, unique = true)
    String methodName;

    @OneToMany(mappedBy = "paymentMethod")
    List<Payment> payments;

    // ---------------- Helper methods ----------------
    public void addPayment(Payment payment) {
        if (!payments.contains(payment)) {
            payments.add(payment);
            payment.setPaymentMethod(this);
        }
    }

    public void removePayment(Payment payment) {
        if (payments.contains(payment)) {
            payments.remove(payment);
            payment.setPaymentMethod(null);
        }
    }
}
