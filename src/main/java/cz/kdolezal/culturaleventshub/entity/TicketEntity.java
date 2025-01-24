package cz.kdolezal.culturaleventshub.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity(name = "tickets")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TicketEntity {

    @Id
    @SequenceGenerator(name = "ticket_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_id_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    @JsonBackReference
    private EventEntity event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    @JsonBackReference
    private PaymentEntity payment;


    @Column(nullable = true)
    @Setter
    private byte[] qrCode;

    @Column(nullable = true)
    @Setter
    private OffsetDateTime purchaseDate;

    @Column(nullable = true)
    @Setter
    private Boolean valid;

    public TicketEntity(EventEntity event, UserEntity user, PaymentEntity payment, byte[] qrCode) {
        this.event = event;
        this.user = user;
        this.payment = payment;
        this.qrCode = qrCode;
    }

    public TicketEntity(EventEntity event, UserEntity user, PaymentEntity payment, Boolean valid) {
        this.event = event;
        this.user = user;
        this.payment = payment;
        this.valid = valid;
    }
}
