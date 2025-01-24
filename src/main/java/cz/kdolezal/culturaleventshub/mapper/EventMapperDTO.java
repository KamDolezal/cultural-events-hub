package cz.kdolezal.culturaleventshub.mapper;

import cz.kdolezal.culturaleventshub.dto.EventDTO;
import cz.kdolezal.culturaleventshub.entity.EventEntity;
import cz.kdolezal.culturaleventshub.entity.TicketEntity;
import org.springframework.stereotype.Component;

@Component
public class EventMapperDTO {
    public EventDTO convertToDto(EventEntity eventEntity) {
        return new EventDTO(
                eventEntity.getId(),
                eventEntity.getName(),
                eventEntity.getDescription(),
                eventEntity.getDate(),
                eventEntity.getVenue(),
                eventEntity.getCapacity(),
                eventEntity.getPrice(),
                eventEntity.getTicketList()
                        .stream()
                        .map(TicketEntity::getId)
                        .toList(),
                eventEntity.getImage()
        );
    }
}