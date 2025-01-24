package cz.kdolezal.culturaleventshub.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "payments")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {
    @Id
    @SequenceGenerator(name = "payment_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_id_seq")
    private Long Id;

    @Setter
    @OneToMany(mappedBy = "payment", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<TicketEntity> tickets = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @Setter
    private UserEntity user;


    @Column
    @Setter
    private Long amount;

    @Column
    @Setter
    private OffsetDateTime paymentDate;

    @Column(nullable = false)
    @Setter
    private String paymentMethod;

    public PaymentEntity(UserEntity user, Long amount, OffsetDateTime paymentDate, String paymentMethod) {
        this.user = user;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
    }

    public PaymentEntity(UserEntity user, String paymentMethod) {
        this.user = user;
        this.paymentMethod = paymentMethod;
    }
}
