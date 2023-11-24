package fr.samyseb.hotelservice.services;

import fr.samyseb.hotelservice.entities.Chambre;
import fr.samyseb.hotelservice.entities.Reservation;
import fr.samyseb.hotelservice.pojos.Offre;
import fr.samyseb.hotelservice.repositories.ChambreRepository;
import fr.samyseb.hotelservice.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OffreService {

    private final HotelService hotelService;
    private final ChambreRepository chambreRepository;
    private final ReservationRepository reservationRepository;

    public List<Offre> create(LocalDate debut, LocalDate fin) {
        List<Chambre> chambres = chambreRepository.findByHotelId(hotelService.identity().id());
        List<Offre> offres = new ArrayList<>();

        for (Chambre chambre : chambres) {
            // Liste des réservations de cette chambre
            List<Reservation> chambresRsv = reservationRepository.findByChambreNumeroAndChambreHotelId(chambre.numero(), hotelService.identity().id());

            LocalDate debutDispo = trouverDebutDisponible(chambresRsv, debut, fin);

            // Si 'debutDispo' est avant 'fin', créer une offre
            if (debutDispo != null && !debutDispo.isAfter(fin)) {
                float prixParNuit = chambre.prix();
                long nombreNuits = ChronoUnit.DAYS.between(debutDispo, fin);
                float prixTotal = prixParNuit * nombreNuits;

                Offre offre = new Offre(hotelService.identity(), prixTotal, chambre, debutDispo, fin);
                offres.add(offre);
            }
        }

        return offres;
    }

    private LocalDate trouverDebutDisponible(List<Reservation> reservations, LocalDate debut, LocalDate fin) {
        LocalDate debutDispo = debut;

        for (Reservation reservation : reservations) {
            if (chevauche(reservation, debut, fin)) {
                LocalDate finRes = reservation.fin().plusDays(1);
                if (finRes.isAfter(debutDispo)) {
                    debutDispo = finRes;
                }
            }
        }

        // Vérifier si la période trouvée est valable
        return debutDispo.isAfter(fin) ? null : debutDispo;
    }

    private boolean chevauche(Reservation reservation, LocalDate debut, LocalDate fin) {
        return !(reservation.fin().isBefore(debut) || reservation.debut().isAfter(fin));
    }

}
