package vn.com.fortis.domain.entity.address;

import vn.com.fortis.constant.CommonConstant;
import vn.com.fortis.domain.entity.BaseEntity;
import vn.com.fortis.domain.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "address")
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    Long id;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "commune")
    private String commune;

    @Column(name = "detail_address")
    private String detailAddress;

    @Builder.Default
    @Column(name = "is_deleted")
    private Boolean isDeleted = CommonConstant.FALSE;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
