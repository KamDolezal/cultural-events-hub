package cz.kdolezal.culturaleventshub.security.expression;

import cz.kdolezal.culturaleventshub.repository.TicketJpaRepository;
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
