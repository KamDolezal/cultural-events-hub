package cz.kdolezal.culturaleventshub.mapper;

import cz.kdolezal.culturaleventshub.dto.PaymentDTO;
import cz.kdolezal.culturaleventshub.entity.PaymentEntity;
import cz.kdolezal.culturaleventshub.entity.TicketEntity;
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
