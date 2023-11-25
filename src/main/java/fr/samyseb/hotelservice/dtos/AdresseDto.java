package fr.samyseb.hotelservice.dtos;

import fr.samyseb.hotelservice.entities.Adresse;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.UUID;

public class AdresseDto {
    private UUID id;
    private String numero;
    private String rue;
    private String ville;
    private String pays;

    public UUID getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getRue() {
        return rue;
    }


    public String getVille() {
        return ville;
    }


    public String getPays() {
        return pays;
    }

    public Adresse toEntity() {
        Adresse.AdresseBuilder builder = Adresse.builder()
                .numero(numero)
                .rue(rue)
                .ville(ville)
                .pays(pays);

        if (id != null) {
            builder.id(id);
        }
        return builder.build();
    }
}
