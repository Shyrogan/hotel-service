package fr.samyseb.hotelservice.dtos;

import fr.samyseb.hotelservice.entities.Adresse;
import fr.samyseb.hotelservice.entities.Chambre;

public class ChambreDto {
    private long numero;
    private float prix;
    private int places;

    public long getNumero() {
        return numero;
    }

    public float getPrix() {
        return prix;
    }

    public int getPlaces() {
        return places;
    }

    public Chambre toEntity() {

        return Chambre.builder()
                .numero(numero)
                .prix(prix)
                .places(places).build();

    }
}
