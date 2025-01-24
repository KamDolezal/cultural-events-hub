package cz.kdolezal.eventmanagementsystem.api;


import cz.kdolezal.eventmanagementsystem.api.request.PaymentAddRequest;
import cz.kdolezal.eventmanagementsystem.api.request.PaymentAddRequestShoppingBasket;
import cz.kdolezal.eventmanagementsystem.dto.PaymentDTO;
import cz.kdolezal.eventmanagementsystem.entity.PaymentEntity;

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
