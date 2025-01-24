package cz.kdolezal.eventmanagementsystem.security.expression;

import cz.kdolezal.eventmanagementsystem.repository.EventJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("eventSecurity")
public class EventSecurity {

    @Autowired
    private EventJpaRepository eventRepository;

    public boolean isOwner(Long eventId, Long userId) {
        return eventRepository.findById(eventId)
                .map(event -> event.getUserId().equals(userId))
                .orElse(false);
    }
}
