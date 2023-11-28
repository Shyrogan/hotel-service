package fr.samyseb.hotelservice.repositories;

import fr.samyseb.hotelservice.entities.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientRepository extends CrudRepository<Client, UUID> {

    Client findClientByNomAndPrenomAndCarteBancaire_Numero(String nom, String prenom, String numero);
}
