package cz.kdolezal.culturaleventshub.repository;

import cz.kdolezal.culturaleventshub.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventJpaRepository extends JpaRepository<EventEntity, Long> {
    Optional<List<EventEntity>> findByUserId(Long userId);
}
