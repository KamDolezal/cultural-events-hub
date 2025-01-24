package cz.kdolezal.culturaleventshub.repository;

import cz.kdolezal.culturaleventshub.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketJpaRepository extends JpaRepository<TicketEntity, Long> {
}
