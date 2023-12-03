package fr.samyseb.hotelservice.repositories;

import fr.samyseb.hotelservice.entities.Chambre;
import fr.samyseb.hotelservice.entities.Hotel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChambreRepository extends CrudRepository<Chambre, Long> {


    List<Chambre> findByHotelId(UUID hotelId);

    Chambre findChambreByNumeroAndHotel_Id(long numero, UUID hotelId);

    void deleteAllByHotel(Hotel hotel);


}
