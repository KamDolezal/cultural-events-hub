package cz.kdolezal.culturaleventshub.service;

import cz.kdolezal.culturaleventshub.api.EventService;
import cz.kdolezal.culturaleventshub.api.exception.BadRequestException;
import cz.kdolezal.culturaleventshub.api.exception.InternalErrorException;
import cz.kdolezal.culturaleventshub.api.exception.ResourceNotFoundException;
import cz.kdolezal.culturaleventshub.api.request.EventAddRequest;
import cz.kdolezal.culturaleventshub.api.request.EventEditRequest;
import cz.kdolezal.culturaleventshub.dto.EventDTO;
import cz.kdolezal.culturaleventshub.entity.EventEntity;
import cz.kdolezal.culturaleventshub.mapper.EventMapperDTO;
import cz.kdolezal.culturaleventshub.repository.EventJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceJpaImpl implements EventService {
    private final EventJpaRepository eventJpaRepository;
    private static final Logger logger = LoggerFactory.getLogger(EventServiceJpaImpl.class);

    private final EventMapperDTO eventMapperDTO;

    public EventServiceJpaImpl(EventJpaRepository eventJpaRepository, EventMapperDTO eventMapperDTO) {
        this.eventJpaRepository = eventJpaRepository;
        this.eventMapperDTO = eventMapperDTO;
    }

    @Override
    public long add(EventAddRequest request) {
        try {
            return eventJpaRepository.save(new EventEntity(request.getName(), request.getDescription(), request.getDate(), request.getVenue(), request.getCapacity(), request.getPrice(), request.getImage(), request.getUserId())).getId();
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Event with name: " + request.getName() + " already exists");
            //TODO add other options of unique parameters
        } catch (DataAccessException e) {
            logger.error("Error while adding event");
            throw new InternalErrorException("Error while adding event");
        }
    }

    @Override
    public void edit(long id, EventEditRequest request) {
        EventEntity eventEntity = eventJpaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event with id: " + id + " was not found"));
        eventEntity.setName(request.getName());
        eventEntity.setDescription(request.getDescription());
        eventEntity.setDate(request.getDate());
        eventEntity.setVenue(request.getVenue());
        eventEntity.setCapacity(request.getCapacity());
        eventEntity.setPrice(request.getPrice());
        eventJpaRepository.save(eventEntity);
    }

    @Override
    public void delete(long id) {
        if (this.get(id) != null) {
            eventJpaRepository.deleteById(id);
        }
    }

    @Override
    public EventDTO get(long id) {
        EventEntity event = eventJpaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event with id: " + id + " was not found"));
        return eventMapperDTO.convertToDto(event);
    }

    @Override
    public EventEntity getEventEntity(long id) {
        return eventJpaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event with id: " + id + " was not found"));
    }

    @Override
    public List<EventEntity> getAllEventEntity() {
        return eventJpaRepository.findAll().stream().toList();
    }

    @Override
    public List<EventDTO> getAll() {
        return eventJpaRepository.findAll().stream().map(eventMapperDTO::convertToDto).toList();
    }

    @Override
    public List<EventDTO> getEventByUserId(long id) {
        List<EventEntity> events = eventJpaRepository.findByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event with user id: " + id + " was not found"));
        return events.stream().map(eventMapperDTO::convertToDto).toList();
    }
}
