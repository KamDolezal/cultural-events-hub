package cz.kdolezal.eventmanagementsystem.security.expression;

import cz.kdolezal.eventmanagementsystem.repository.TicketJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("ticketSecurity")
public class TicketSecurity {

    @Autowired
    private TicketJpaRepository ticketRepository;

    public boolean isOwner(Long ticketId, Long userId) {
        return ticketRepository.findById(ticketId)
                .map(ticket -> ticket.getUser().getId().equals(userId))
                .orElse(false);
    }
}
