package vn.com.fortis.repository;

import vn.com.fortis.domain.entity.product.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    boolean existsByPromotionCode(String promotionCode);

    Optional<Promotion> findByPromotionCode(String promotionCode);
}
