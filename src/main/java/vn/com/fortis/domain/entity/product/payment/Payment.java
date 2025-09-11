package vn.com.fortis.domain.entity.product.payment;

import vn.com.fortis.domain.entity.BaseEntity;
import vn.com.fortis.domain.entity.product.Order;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;

@Entity
@Table(name = "payments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment extends BaseEntity {

  @Id
  @GeneratedValue
  @UuidGenerator
  @Column(name = "id", insertable = false, updatable = false, nullable = false, columnDefinition = "CHAR(36)")
  String id;

  LocalDate paymente;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "payment_method_id", nullable = false)
  PaymentMethod paymentMethod;


}
