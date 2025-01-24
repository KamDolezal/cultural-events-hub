package cz.kdolezal.eventmanagementsystem.mapper;

import cz.kdolezal.eventmanagementsystem.dto.EventDTO;
import cz.kdolezal.eventmanagementsystem.entity.EventEntity;
import cz.kdolezal.eventmanagementsystem.entity.TicketEntity;
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