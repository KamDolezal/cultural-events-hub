package cz.kdolezal.eventmanagementsystem.dto;

import lombok.Value;

import java.time.OffsetDateTime;
import java.util.List;

@Value
public class PaymentDTO {
    Long id;
    List<Long> ticketIdList;
    Long userId;
    Long amount;
    OffsetDateTime paymentDate;
    String paymentMethod;
}
