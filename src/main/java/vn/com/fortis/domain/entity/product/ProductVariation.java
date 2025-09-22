package vn.com.fortis.domain.entity.product;

import vn.com.fortis.domain.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "product-variations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String color;

    @Column(nullable = false)
    String size;

    @Column(nullable = false)
    Double price;

    @Column(nullable = false)
    Integer inventoryQuantity;


    @Column()
    Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "productVariation", fetch = FetchType.LAZY)
    @JsonIgnore
    Media media;
}