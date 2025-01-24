package cz.kdolezal.eventmanagementsystem.mapper;

import cz.kdolezal.eventmanagementsystem.dto.TicketDTO;
import cz.kdolezal.eventmanagementsystem.entity.TicketEntity;
import org.springframework.stereotype.Component;

@Component
public class TicketMapperDTO {
    public TicketDTO covertToDto(TicketEntity ticketEntity) {
        return new TicketDTO(
                ticketEntity.getId(),
                ticketEntity.getEvent() != null ? ticketEntity.getEvent().getId() : null,
                ticketEntity.getUser() != null ? ticketEntity.getUser().getId() : null,
                ticketEntity.getPayment() != null ? ticketEntity.getPayment().getId() : null,
                ticketEntity.getQrCode(),
                ticketEntity.getPurchaseDate(),
                ticketEntity.getValid()
        );
    }
}
