package fr.samyseb.hotelservice.controllers;

import fr.samyseb.hotelservice.entities.Client;
import fr.samyseb.hotelservice.entities.Reservation;
import fr.samyseb.hotelservice.pojos.Offre;
import fr.samyseb.hotelservice.repositories.ReservationRepository;
import fr.samyseb.hotelservice.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    @GetMapping("/{chambre}")
    public List<Reservation> getReservationChambre(@PathVariable long chambre) {
        return reservationRepository.findAllByChambreNumero(chambre);
    }

    @PutMapping("/")
    public Reservation createReservation(Offre offre, Client client) {
        return reservationService.reserver(offre, client);
    }

    @PatchMapping("/")
    public Reservation updateReservation() {
        return null;
    }

}
