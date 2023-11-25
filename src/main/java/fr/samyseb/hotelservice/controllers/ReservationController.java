package fr.samyseb.hotelservice.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.samyseb.hotelservice.entities.Client;
import fr.samyseb.hotelservice.entities.Reservation;
import fr.samyseb.hotelservice.pojos.Offre;
import fr.samyseb.hotelservice.repositories.ReservationRepository;
import fr.samyseb.hotelservice.services.ReservationService;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

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
    public Reservation createReservation(@RequestBody ReservationRequest request,
                                         @RequestHeader("Authorization") String authHeader) {


        String[] credentials = decodeBasicAuth(authHeader);
        UUID agenceId = UUID.fromString(credentials[0]);
        String agencePassword = credentials.length > 1 ? credentials[1] : "";

        return reservationService.reserver(request.offre(), request.client(), agenceId, agencePassword);
    }

    @PatchMapping("/")
    public Reservation updateReservation() {
        return null;
    }

    private String[] decodeBasicAuth(String authHeader) {
        String base64Credentials = authHeader.substring("Basic ".length());
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String decodedCredentials = new String(credDecoded, StandardCharsets.UTF_8);
        return decodedCredentials.split(":", 2);
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter(onMethod = @__(@JsonProperty))
    public static class ReservationRequest {
        private Offre offre;
        private Client client;
    }
}
