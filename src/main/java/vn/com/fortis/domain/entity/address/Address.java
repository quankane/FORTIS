package vn.com.fortis.domain.entity.address;

import vn.com.fortis.domain.entity.BaseEntity;
import vn.com.fortis.domain.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "address")
public class Address extends BaseEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(insertable = false, updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    String id;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "commune")
    private String commune;

    @Column(name = "detail-address")
    private String detailAddress;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
