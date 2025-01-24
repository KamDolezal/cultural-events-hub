package cz.kdolezal.eventmanagementsystem.service;

import cz.kdolezal.eventmanagementsystem.api.PaymentService;
import cz.kdolezal.eventmanagementsystem.api.UserService;
import cz.kdolezal.eventmanagementsystem.api.exception.InternalErrorException;
import cz.kdolezal.eventmanagementsystem.api.exception.ResourceNotFoundException;
import cz.kdolezal.eventmanagementsystem.api.request.PaymentAddRequest;
import cz.kdolezal.eventmanagementsystem.api.request.PaymentAddRequestShoppingBasket;
import cz.kdolezal.eventmanagementsystem.dto.PaymentDTO;
import cz.kdolezal.eventmanagementsystem.entity.PaymentEntity;
import cz.kdolezal.eventmanagementsystem.entity.UserEntity;
import cz.kdolezal.eventmanagementsystem.mapper.PaymentMapperDTO;
import cz.kdolezal.eventmanagementsystem.repository.PaymentJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaymentServiceJpaImpl implements PaymentService {
    private final PaymentJpaRepository paymentJpaRepository;
    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceJpaImpl.class);

    private final UserService userService;

    private final PaymentMapperDTO paymentMapperDTO;

    public PaymentServiceJpaImpl(PaymentJpaRepository paymentJpaRepository, UserService userService, PaymentMapperDTO paymentMapperDTO) {
        this.paymentJpaRepository = paymentJpaRepository;
        this.userService = userService;
        this.paymentMapperDTO = paymentMapperDTO;
    }


    @Override
    public long add(PaymentAddRequestShoppingBasket request) {
        try {
            UserEntity user = userService.getUserEntity(request.getUserId());
            return paymentJpaRepository.save(new PaymentEntity(user, "shopping-basket")).getId();
        } catch (DataAccessException e) {
            logger.error("Error while adding payment", e);
            throw new InternalErrorException("Error while adding payment");
        }
    }

    //old version of add method
    @Override
    public long addOld(PaymentAddRequest request) {
        try {
            UserEntity user = userService.getUserEntity(request.getUser_id());
            return paymentJpaRepository.save(new PaymentEntity(user, request.getAmount(), request.getPaymentDate(), request.getPaymentMethod())).getId();
        } catch (DataAccessException e) {
            logger.error("Error while adding old payment");
            throw new InternalErrorException("Error while adding old payment");
        }
    }

    @Override
    public void delete(long id) {
        if (this.get(id) != null) {
            paymentJpaRepository.deleteById(id);
        }
    }

    @Override
    public PaymentDTO get(long id) {
        return paymentMapperDTO.convertToDto(paymentJpaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment with id: " + id + " was not found")));
    }

    @Override
    public PaymentEntity getPaymentEntity(long id) {
        return paymentJpaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment with id: " + id + " was not found"));
    }

    @Override
    public List<PaymentEntity> getAllPaymentEntity() {
        return paymentJpaRepository.findAll().stream().toList();
    }

    @Override
    public List<PaymentDTO> getAll() {
        return paymentJpaRepository.findAll().stream().map(paymentMapperDTO::convertToDto).toList();
    }

    @Override
    public PaymentDTO getOrderPaymentByUserId(long id) {
        List<PaymentEntity> paymentEntities = paymentJpaRepository.findByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment with id: " + id + " was not found"));
        PaymentEntity paymentEntity = new PaymentEntity();
        for (PaymentEntity payEnt : paymentEntities) {
            if (payEnt.getPaymentMethod().equals("shopping-basket")) {
                paymentEntity = payEnt;
            }
        }
        if (paymentEntity.getId() != null) {
            return paymentMapperDTO.convertToDto(paymentEntity);
        } else {
            return null;
        }

    }
}
