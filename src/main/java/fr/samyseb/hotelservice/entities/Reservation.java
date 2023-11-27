package fr.samyseb.hotelservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "reservation")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter(onMethod = @__(@JsonProperty))
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(cascade = CascadeType.REMOVE)
    private Agence agence;
    @ManyToOne(cascade = CascadeType.REMOVE)
    private Hotel hotel;
    @ManyToOne(cascade = CascadeType.REMOVE)
    private Chambre chambre;
    @ManyToOne
    private Client client;
    private LocalDate debut;
    private LocalDate fin;

}
