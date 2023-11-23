package fr.samyseb.hotelservice.repositories;

import fr.samyseb.hotelservice.entities.Adresse;
import fr.samyseb.hotelservice.entities.CarteBancaire;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CarteBancaireRepository extends CrudRepository<CarteBancaire, String> {
}
