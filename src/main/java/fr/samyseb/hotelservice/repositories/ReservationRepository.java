package fr.samyseb.hotelservice.repositories;

import fr.samyseb.hotelservice.entities.Chambre;
import fr.samyseb.hotelservice.entities.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, UUID> {

    List<Reservation> findAllByChambreNumero(long chambre_numero);

    List<Reservation> findByChambreNumeroAndChambreHotelId(long chambreNumero, UUID hotelId);

    Reservation findTopByChambreAndFinBeforeOrderByFinDesc(Chambre chambre, LocalDate debut);
}
