package fr.samyseb.hotelservice.services;

import fr.samyseb.hotelservice.entities.Client;
import fr.samyseb.hotelservice.entities.Reservation;
import fr.samyseb.hotelservice.pojos.Offre;
import fr.samyseb.hotelservice.repositories.ChambreRepository;
import fr.samyseb.hotelservice.repositories.ClientRepository;
import fr.samyseb.hotelservice.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final HotelService hotelService;
    private final ChambreRepository chambreRepository;
    private final ClientRepository clientRepository;
    private final ReservationRepository reservationRepository;

    public Reservation reserver(Offre offre, Client fillableClient) {
        // L'hôtel est bien celui-ci
        if (!hotelService.identity().equals(offre.hotel())) {
            throw new IllegalArgumentException("hotel");
        }

        // La chambre existe
        var chambre = chambreRepository.findById(offre.chambre().numero())
                .orElseThrow(() -> new IllegalArgumentException("chambre"));

        // Soit le client n'a pas d'ID et on l'insère,
        // soit il en a un et donc on le recherche par son ID.
        var client = fillableClient.id() == null ?
                clientRepository.save(fillableClient) :
                clientRepository.findById(fillableClient.id())
                        .orElseThrow(() -> new IllegalArgumentException("client"));

        // TODO: vérifier dates

        return reservationRepository.save(Reservation.builder()
                .hotel(offre.hotel())
                .chambre(chambre)
                .debut(offre.debut())
                .fin(offre.fin())
                .client(client)
                .build());
    }

}
