package fr.samyseb.hotelservice.repositories;

import fr.samyseb.hotelservice.entities.Chambre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChambreRepository extends CrudRepository<Chambre, Long> {
}
