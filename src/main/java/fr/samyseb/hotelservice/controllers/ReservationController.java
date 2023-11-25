package fr.samyseb.hotelservice.controllers;

import fr.samyseb.hotelservice.dtos.ClientDto;
import fr.samyseb.hotelservice.dtos.OffreDto;
import fr.samyseb.hotelservice.entities.Client;
import fr.samyseb.hotelservice.entities.Reservation;
import fr.samyseb.hotelservice.pojos.Offre;
import fr.samyseb.hotelservice.repositories.ReservationRepository;
import fr.samyseb.hotelservice.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    @GetMapping("/{chambre}")
    public List<Reservation> getReservationChambre(@PathVariable long chambre) {
        return reservationRepository.findAllByChambreNumero(chambre);
    }

    @PostMapping("/")
    public Reservation createReservation(@RequestBody ReservationRequest request) {
        return reservationService.reserver(request.getOffre().toEntity(), request.getClient().toEntity());
    }

    @PatchMapping("/")
    public Reservation updateReservation() {
        return null;
    }


    public static class ReservationRequest {
        private OffreDto offre;
        private ClientDto client;

        public ReservationRequest() {
        }

        public OffreDto getOffre() {
            return offre;
        }

        public ClientDto getClient() {
            return client;
        }
    }

}
