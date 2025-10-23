package vn.com.fortis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MomoTransactionRepository extends JpaRepository<MomoTransaction, String> {

    Optional<MomoTransaction> findByMomoOrderId(@Param("momoOrderId") String momoOrderId);

}