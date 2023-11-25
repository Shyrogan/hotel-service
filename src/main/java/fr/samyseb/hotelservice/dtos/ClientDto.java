package fr.samyseb.hotelservice.dtos;

import fr.samyseb.hotelservice.entities.CarteBancaire;
import fr.samyseb.hotelservice.entities.Client;

import java.util.UUID;

public class ClientDto {
    private UUID id;
    private String nom;
    private String prenom;

    private CarteBancaireDto carteBancaire;

    public UUID getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public CarteBancaireDto getCarteBancaire() {
        return carteBancaire;
    }

    public Client toEntity() {
        // Création de l'entité Client avec le builder
        Client.ClientBuilder builder = Client.builder()
                .nom(this.nom)
                .prenom(this.prenom);

        if (this.id != null) {
            builder.id(this.id);
        }

        Client client = builder.build();
        CarteBancaire cb = this.carteBancaire.toEntity();

        cb.client(client);
        client.carteBancaire(cb);

        return client;
    }

}
