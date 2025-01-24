package cz.kdolezal.eventmanagementsystem.repository;

import cz.kdolezal.eventmanagementsystem.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventJpaRepository extends JpaRepository<EventEntity, Long> {
    Optional<List<EventEntity>> findByUserId(Long userId);
}
