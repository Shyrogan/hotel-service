package fr.samyseb.hotelservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "hotel")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private int etoiles;
    @OneToOne(cascade = CascadeType.REMOVE)
    private Adresse adresse;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Chambre> chambres;
    private URL url;


}
