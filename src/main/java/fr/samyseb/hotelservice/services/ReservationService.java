package fr.samyseb.hotelservice.services;

import fr.samyseb.hotelservice.entities.Agence;
import fr.samyseb.hotelservice.entities.Client;
import fr.samyseb.hotelservice.entities.Reservation;
import fr.samyseb.hotelservice.pojos.Offre;
import fr.samyseb.hotelservice.repositories.AgenceRepository;
import fr.samyseb.hotelservice.repositories.ChambreRepository;
import fr.samyseb.hotelservice.repositories.ClientRepository;
import fr.samyseb.hotelservice.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final HotelService hotelService;
    private final OffreService offreService;
    private final ChambreRepository chambreRepository;
    private final ClientRepository clientRepository;
    private final AgenceRepository agenceRepository;
    private final ReservationRepository reservationRepository;

    public Reservation reserver(Offre offre, Client fillableClient, UUID agenceId, String agencePassword) {

        Agence agence = agenceRepository.findById(agenceId)
                .orElseThrow(() -> new IllegalArgumentException("Agence introuvable"));

        if (!agence.motDePasse().equals(agencePassword)) {
            throw new IllegalArgumentException("Mot de passe incorrect");
        }

        // L'hôtel est bien celui-ci
        if (!hotelService.identity().id().equals(offre.hotel().id())) {
            throw new IllegalArgumentException("hotel");
        }

        // La chambre existe
        var chambre = chambreRepository.findById(offre.chambre().id())
                .orElseThrow(() -> new IllegalArgumentException("chambre"));


        var client = clientRepository.findClientByNomAndPrenomAndCarteBancaire_Numero(
                fillableClient.nom(), fillableClient.prenom(), fillableClient.carteBancaire().numero());
        if (client == null) {
            client = clientRepository.save(fillableClient);

        }
        List<Reservation> chmabreRsv = reservationRepository.findByChambreNumeroAndChambreHotelId(offre.chambre().numero(), hotelService.identity().id());
        for (Reservation rsv : chmabreRsv) {
            if (offreService.chevauche(rsv, offre.debut(), offre.fin())) {
                throw new RuntimeException("chambre non disponibles sur ces dates");
            }

        }


        return reservationRepository.save(Reservation.builder()
                .hotel(offre.hotel())
                .agence(agence)
                .chambre(chambre)
                .debut(offre.debut())
                .fin(offre.fin())
                .client(client)
                .build());
    }

}
