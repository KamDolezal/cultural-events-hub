package cz.kdolezal.eventmanagementsystem.repository;

import cz.kdolezal.eventmanagementsystem.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, Long> {
    Optional<List<PaymentEntity>> findByUserId(Long userId);
}
