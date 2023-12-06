package fr.samyseb.hotelservice.repositories;

import fr.samyseb.hotelservice.entities.Hotel;
import fr.samyseb.hotelservice.entities.Partenariat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PartenariatRepository extends CrudRepository<Partenariat, UUID> {

    Optional<Partenariat> findByHotelIdAndAgenceId(UUID hotelId, UUID agenceId);

    void deleteAllByHotel(Hotel hotel);

}
