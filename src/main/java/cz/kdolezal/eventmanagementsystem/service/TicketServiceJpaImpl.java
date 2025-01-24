package cz.kdolezal.eventmanagementsystem.service;

import cz.kdolezal.eventmanagementsystem.api.EventService;
import cz.kdolezal.eventmanagementsystem.api.PaymentService;
import cz.kdolezal.eventmanagementsystem.api.TicketService;
import cz.kdolezal.eventmanagementsystem.api.UserService;
import cz.kdolezal.eventmanagementsystem.api.exception.InternalErrorException;
import cz.kdolezal.eventmanagementsystem.api.exception.ResourceNotFoundException;
import cz.kdolezal.eventmanagementsystem.api.request.TicketAddQrRequest;
import cz.kdolezal.eventmanagementsystem.api.request.TicketAddRequest;
import cz.kdolezal.eventmanagementsystem.dto.TicketDTO;
import cz.kdolezal.eventmanagementsystem.entity.EventEntity;
import cz.kdolezal.eventmanagementsystem.entity.PaymentEntity;
import cz.kdolezal.eventmanagementsystem.entity.TicketEntity;
import cz.kdolezal.eventmanagementsystem.entity.UserEntity;
import cz.kdolezal.eventmanagementsystem.mapper.TicketMapperDTO;
import cz.kdolezal.eventmanagementsystem.repository.EventJpaRepository;
import cz.kdolezal.eventmanagementsystem.repository.TicketJpaRepository;
import cz.kdolezal.eventmanagementsystem.repository.UserJpaRepository;
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
