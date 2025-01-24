package cz.kdolezal.culturaleventshub.security.expression;

import cz.kdolezal.culturaleventshub.repository.EventJpaRepository;
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
