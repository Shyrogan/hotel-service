package fr.samyseb.hotelservice.controllers;

import fr.samyseb.hotelservice.pojos.Offre;
import fr.samyseb.hotelservice.services.OffreService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/consultation")
@RequiredArgsConstructor
public class ConsultationController {

    private final OffreService offreService;

    @GetMapping
    public List<Offre> getOffres(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin,
                                 @RequestParam(required = false) Float prixMin,
                                 @RequestParam(required = false) Float prixMax,
                                 @RequestHeader("Authorization") String authHeader) {


        String[] credentials = decodeBasicAuth(authHeader);
        UUID agenceId = UUID.fromString(credentials[0]);
        String agencePassword = credentials.length > 1 ? credentials[1] : "";

        return offreService.create(debut, fin, prixMin, prixMax, agenceId, agencePassword);
    }

    private String[] decodeBasicAuth(String authHeader) {
        String base64Credentials = authHeader.substring("Basic ".length());
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String decodedCredentials = new String(credDecoded, StandardCharsets.UTF_8);
        return decodedCredentials.split(":", 2);
    }
}
