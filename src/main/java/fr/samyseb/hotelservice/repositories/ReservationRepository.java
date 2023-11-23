package fr.samyseb.hotelservice.repositories;

import fr.samyseb.hotelservice.entities.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, UUID> {

    List<Reservation> findAllByChambreNumero(long chambre_numero);

}
