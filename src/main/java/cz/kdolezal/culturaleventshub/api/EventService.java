package cz.kdolezal.eventmanagementsystem.api;

import cz.kdolezal.eventmanagementsystem.api.request.EventAddRequest;
import cz.kdolezal.eventmanagementsystem.api.request.EventEditRequest;
import cz.kdolezal.eventmanagementsystem.dto.EventDTO;
import cz.kdolezal.eventmanagementsystem.entity.EventEntity;

import java.util.List;

public interface EventService {
    long add(EventAddRequest request);

    void edit(long id, EventEditRequest request);

    void delete(long id);

    EventDTO get(long id);

    EventEntity getEventEntity(long id);

    List<EventEntity> getAllEventEntity();

    List<EventDTO> getAll();

    List<EventDTO> getEventByUserId(long id);
}
