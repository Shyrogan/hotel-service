package fr.samyseb.hotelservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.net.URL;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "hotel")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter(onMethod = @__(@JsonProperty))
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private int etoiles;
    @OneToOne(cascade = CascadeType.REMOVE)
    private Adresse adresse;
    @OneToMany(cascade = CascadeType.REMOVE)
    @Getter(onMethod = @__(@JsonIgnore))
    private List<Chambre> chambres;
    private URL url;

}
