package cz.kdolezal.culturaleventshub.service;

import cz.kdolezal.culturaleventshub.api.EventService;
import cz.kdolezal.culturaleventshub.api.PaymentService;
import cz.kdolezal.culturaleventshub.api.TicketService;
import cz.kdolezal.culturaleventshub.api.UserService;
import cz.kdolezal.culturaleventshub.api.exception.InternalErrorException;
import cz.kdolezal.culturaleventshub.api.exception.ResourceNotFoundException;
import cz.kdolezal.culturaleventshub.api.request.TicketAddQrRequest;
import cz.kdolezal.culturaleventshub.api.request.TicketAddRequest;
import cz.kdolezal.culturaleventshub.dto.TicketDTO;
import cz.kdolezal.culturaleventshub.entity.EventEntity;
import cz.kdolezal.culturaleventshub.entity.PaymentEntity;
import cz.kdolezal.culturaleventshub.entity.TicketEntity;
import cz.kdolezal.culturaleventshub.entity.UserEntity;
import cz.kdolezal.culturaleventshub.mapper.TicketMapperDTO;
import cz.kdolezal.culturaleventshub.repository.EventJpaRepository;
import cz.kdolezal.culturaleventshub.repository.TicketJpaRepository;
import cz.kdolezal.culturaleventshub.repository.UserJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceJpaImpl implements TicketService {

    private final TicketJpaRepository ticketJpaRepository;
    private final EventService eventService;
    private final UserService userService;
    private final PaymentService paymentService;

    private static final Logger logger = LoggerFactory.getLogger(TicketServiceJpaImpl.class);
    private final TicketMapperDTO ticketMapperDTO;

    public TicketServiceJpaImpl(TicketJpaRepository ticketJpaRepository, EventJpaRepository eventJpaRepository, UserJpaRepository userJpaRepository, EventService eventService, UserService userService, PaymentService paymentService, TicketMapperDTO ticketMapperDTO) {
        this.ticketJpaRepository = ticketJpaRepository;
        this.eventService = eventService;
        this.userService = userService;
        this.paymentService = paymentService;
        this.ticketMapperDTO = ticketMapperDTO;
    }

    @Override
    public long add(TicketAddRequest request) {
        final EventEntity eventEntity = eventService.getEventEntity(request.getEventId());
        final UserEntity userEntity = userService.getUserEntity(request.getUserId());
        final PaymentEntity paymentEntity = paymentService.getPaymentEntity(request.getPaymentId());
        try {
            return ticketJpaRepository.save(new TicketEntity(eventEntity, userEntity, paymentEntity, true)).getId();
        } catch (DataAccessException e) {
            logger.error("Error while adding ticket");
            throw new InternalErrorException("Error while adding ticket");
        }
    }

    @Override
    public void addQr(TicketAddQrRequest request) {
        final TicketEntity ticket = getTicketEntity(request.getTicketId());
        ticket.setQrCode(request.getQrCode());
        ticketJpaRepository.save(ticket);
    }

    @Override
    public void delete(long id) {
        if (this.get(id) != null) {
            ticketJpaRepository.deleteById(id);
        }

    }

    @Override
    public TicketEntity getTicketEntity(long id) {
        return ticketJpaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket with id: " + id + " was not found"));
    }

    @Override
    public TicketDTO get(long id) {
        TicketEntity ticket = ticketJpaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket with id: " + id + " was not found"));
        return ticketMapperDTO.covertToDto(ticket);
    }

    @Override
    public List<TicketDTO> getAll() {
        return ticketJpaRepository.findAll().stream().map(ticketMapperDTO::covertToDto).toList();
    }

    @Override
    public List<TicketEntity> getAllTicketEntity() {
        return ticketJpaRepository.findAll().stream().toList();
    }
}
