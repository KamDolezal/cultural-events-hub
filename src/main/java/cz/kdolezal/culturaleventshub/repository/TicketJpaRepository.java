package cz.kdolezal.eventmanagementsystem.repository;

import cz.kdolezal.eventmanagementsystem.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketJpaRepository extends JpaRepository<TicketEntity, Long> {
}
