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

    public void setNumero(long numero) {
        this.numero = numero;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public Chambre toEntity() {

        return Chambre.builder()
                .numero(numero)
                .prix(prix)
                .places(places).build();

    }
}
