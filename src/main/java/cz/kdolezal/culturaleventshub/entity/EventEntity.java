package cz.kdolezal.eventmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "events")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity {

    @Id
    @SequenceGenerator(name = "event_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_id_seq")
    private Long id;

    @Column(nullable = false)
    @Setter
    private String name;

    @Column(nullable = true)
    @Setter
    private String description;

    @Column(nullable = false)
    @Setter
    private OffsetDateTime date;

    @Column(nullable = false)
    @Setter
    private String venue;

    @Column(nullable = true)
    @Setter
    private Long capacity;

    @Column(nullable = false)
    @Setter
    private Long price;

    @Column
    @Setter
    private String image;

    @Column(nullable = false)
    @Setter
    private Long userId;

    @OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<TicketEntity> ticketList = new ArrayList<>();

    public EventEntity(String name, String description, OffsetDateTime date, String venue, Long capacity, Long price, String image, Long userId) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.venue = venue;
        this.capacity = capacity;
        this.price = price;
        this.image = image;
        this.userId = userId;
    }
}
