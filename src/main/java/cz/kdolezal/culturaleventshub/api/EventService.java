package cz.kdolezal.culturaleventshub.api;

import cz.kdolezal.culturaleventshub.api.request.EventAddRequest;
import cz.kdolezal.culturaleventshub.api.request.EventEditRequest;
import cz.kdolezal.culturaleventshub.dto.EventDTO;
import cz.kdolezal.culturaleventshub.entity.EventEntity;

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
