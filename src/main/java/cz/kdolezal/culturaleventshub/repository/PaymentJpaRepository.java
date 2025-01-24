package cz.kdolezal.culturaleventshub.repository;

import cz.kdolezal.culturaleventshub.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, Long> {
    Optional<List<PaymentEntity>> findByUserId(Long userId);
}
