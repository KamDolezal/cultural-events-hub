package cz.kdolezal.eventmanagementsystem.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketAddRequest {
    private Long eventId;
    private Long userId;
    private Long paymentId;
}
