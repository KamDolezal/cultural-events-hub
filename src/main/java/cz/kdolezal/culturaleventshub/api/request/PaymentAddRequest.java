package cz.kdolezal.culturaleventshub.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAddRequest {
    private Long user_id;
    private Long amount;
    private OffsetDateTime paymentDate;
    private String paymentMethod;
}
