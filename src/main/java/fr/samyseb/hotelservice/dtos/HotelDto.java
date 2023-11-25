package fr.samyseb.hotelservice.dtos;

import fr.samyseb.hotelservice.entities.Hotel;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class HotelDto {
    private UUID id;
    private String nom;
    private int etoiles;
    private AdresseDto adresse;
    private String url;

    public UUID getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public int getEtoiles() {
        return etoiles;
    }

    public AdresseDto getAdresse() {
        return adresse;
    }

    public String getUrl() {
        return url;
    }

    public Hotel toEntity() {
        Hotel.HotelBuilder builder = Hotel.builder()
                .nom(nom)
                .etoiles(etoiles)
                .adresse(adresse.toEntity());

        if (id != null) {
            builder.id(id);
        }

        try {
            builder.url(new URL(url));
        } catch (MalformedURLException e) {

        }

        return builder.build();
    }
}


