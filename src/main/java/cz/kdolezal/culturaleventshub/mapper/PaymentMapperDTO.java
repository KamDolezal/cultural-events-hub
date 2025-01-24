package cz.kdolezal.eventmanagementsystem.mapper;

import cz.kdolezal.eventmanagementsystem.dto.PaymentDTO;
import cz.kdolezal.eventmanagementsystem.entity.PaymentEntity;
import cz.kdolezal.eventmanagementsystem.entity.TicketEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapperDTO {

    public PaymentDTO convertToDto(PaymentEntity paymentEntity) {
        return new PaymentDTO(
                paymentEntity.getId(),
                paymentEntity.getTickets()
                        .stream()
                        .map(TicketEntity::getId)
                        .toList(),
                paymentEntity.getUser().getId(),
                paymentEntity.getAmount(),
                paymentEntity.getPaymentDate(),
                paymentEntity.getPaymentMethod()
        );
    }
}
