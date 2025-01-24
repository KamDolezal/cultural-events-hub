package cz.kdolezal.culturaleventshub.dto;

import lombok.Value;

import java.time.OffsetDateTime;
import java.util.List;

@Value
public class EventDTO {
    Long id;
    String name;
    String description;
    OffsetDateTime date;
    String venue;
    Long capacity;
    Long price;
    List<Long> ticketIdList;
    String image;
}
