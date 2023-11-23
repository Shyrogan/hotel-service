package fr.samyseb.hotelservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "chambre")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter(onMethod = @__(@JsonProperty))
public class Chambre {

    // On ne génère pas l'id
    @Id
    private int numero;
    private float prix;
    private int places;
    @ManyToOne
    private Hotel hotel;

}
