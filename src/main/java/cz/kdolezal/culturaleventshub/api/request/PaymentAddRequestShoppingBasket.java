package cz.kdolezal.eventmanagementsystem.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAddRequestShoppingBasket {
    private Long userId;
}
