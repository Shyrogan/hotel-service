package fr.samyseb.hotelservice.dtos;

import fr.samyseb.hotelservice.entities.CarteBancaire;
import fr.samyseb.hotelservice.entities.Client;

public class CarteBancaireDto {
    private String numero;
    private int mois;
    private int annee;

    private String cryptogramme;

    public String getNumero() {
        return numero;
    }

    public int getMois() {
        return mois;
    }

    public int getAnnee() {
        return annee;
    }

    public String getCryptogramme() {
        return cryptogramme;
    }

    public CarteBancaire toEntity() {
        return CarteBancaire.builder()
                .numero(numero)
                .mois(mois)
                .annee(annee)
                .cryptogramme(cryptogramme)
                .build();
    }
}
