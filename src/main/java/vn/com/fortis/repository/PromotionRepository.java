package vn.com.fortis.repository;

import vn.com.fortis.domain.entity.product.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    Optional<Promotion> findByPromotionCodeAndIsDeletedFalse(String promotionCode);

    Boolean existsByPromotionCodeAndIsDeletedFalse(String promotionCode);

    Boolean existsByPromotionCodeAndIsDeletedTrue(String promotionCode);

    Optional<Promotion> findByIdAndIsDeletedFalse(Long id);
}
