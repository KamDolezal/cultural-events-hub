package cz.kdolezal.eventmanagementsystem.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventAddRequest {
    private String name;
    private String description;
    private OffsetDateTime date;
    private String venue;
    private Long capacity;
    private Long price;
    private String image;
    private Long userId;
}
