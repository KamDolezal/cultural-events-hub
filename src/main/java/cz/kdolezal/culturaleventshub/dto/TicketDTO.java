package cz.kdolezal.culturaleventshub.dto;

import lombok.Value;

import java.time.OffsetDateTime;

@Value
public class TicketDTO {
    Long id;
    Long eventId;
    Long userId;
    Long paymentId;
    byte[] qrCode;
    OffsetDateTime purchaseDate;
    Boolean valid;
}
