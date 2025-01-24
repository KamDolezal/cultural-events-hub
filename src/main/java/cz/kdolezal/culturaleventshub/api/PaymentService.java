package cz.kdolezal.culturaleventshub.api;


import cz.kdolezal.culturaleventshub.api.request.PaymentAddRequest;
import cz.kdolezal.culturaleventshub.api.request.PaymentAddRequestShoppingBasket;
import cz.kdolezal.culturaleventshub.dto.PaymentDTO;
import cz.kdolezal.culturaleventshub.entity.PaymentEntity;

import java.util.List;

public interface PaymentService {
    long addOld(PaymentAddRequest request);

    long add(PaymentAddRequestShoppingBasket request);

    void delete(long id);

    PaymentDTO get(long id);

    PaymentEntity getPaymentEntity(long id);

    List<PaymentEntity> getAllPaymentEntity();

    List<PaymentDTO> getAll();

    PaymentDTO getOrderPaymentByUserId(long id);
}
