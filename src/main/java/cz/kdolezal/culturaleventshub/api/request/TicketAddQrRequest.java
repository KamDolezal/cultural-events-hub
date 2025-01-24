package cz.kdolezal.culturaleventshub.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketAddQrRequest {
    private long ticketId;
    private byte[] qrCode;
}
