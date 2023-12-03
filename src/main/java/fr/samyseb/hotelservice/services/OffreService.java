package fr.samyseb.hotelservice.services;

import fr.samyseb.hotelservice.entities.Agence;
import fr.samyseb.hotelservice.entities.Chambre;
import fr.samyseb.hotelservice.entities.Partenariat;
import fr.samyseb.hotelservice.entities.Reservation;
import fr.samyseb.hotelservice.pojos.Offre;
import fr.samyseb.hotelservice.repositories.AgenceRepository;
import fr.samyseb.hotelservice.repositories.ChambreRepository;
import fr.samyseb.hotelservice.repositories.PartenariatRepository;
import fr.samyseb.hotelservice.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OffreService {

    private final HotelService hotelService;
    private final ChambreRepository chambreRepository;
    private final ReservationRepository reservationRepository;
    private final AgenceRepository agenceRepository;
    private final PartenariatRepository partenariatRepository;


    public List<Offre> create(LocalDate debut, LocalDate fin, Float prixMin, Float prixMax, UUID agenceId, String agencePassword) {
        // Vérification de l'agence
        Agence agence = agenceRepository.findById(agenceId)
                .orElseThrow(() -> new IllegalArgumentException("Agence introuvable"));

        if (!agence.motDePasse().equals(agencePassword)) {
            throw new IllegalArgumentException("Mot de passe incorrect");
        }

        // Trouver un partenariat entre l'hôtel et l'agence
        Optional<Partenariat> partenariatOpt = partenariatRepository.findByHotelIdAndAgenceId(hotelService.identity().id(), agenceId);

        float facteurReduction = partenariatOpt.map(Partenariat::reduction).orElse(1.0f);

        List<Chambre> chambres = chambreRepository.findByHotel(hotelService.identity());
        List<Offre> offres = new ArrayList<>();

        for (Chambre chambre : chambres) {
            // Liste des réservations de cette chambre
            List<Reservation> chambresRsv = reservationRepository.findByChambreNumeroAndChambreHotelId(chambre.numero(), hotelService.identity().id());

            LocalDate debutDispo = trouverDebutDisponible(chambresRsv, debut, fin);

            // Si 'debutDispo' est avant 'fin', créer une offre
            if (debutDispo != null && !debutDispo.isAfter(fin)) {
                float prixParNuit = chambre.prix();
                long nombreNuits = ChronoUnit.DAYS.between(debutDispo, fin);
                float prixTotal = prixParNuit * nombreNuits * facteurReduction;

                if ((prixMin == null || prixTotal >= prixMin) && (prixMax == null || prixTotal <= prixMax)) {
                    Offre offre = new Offre(agence, hotelService.identity(), prixTotal, chambre, debutDispo, fin);
                    offres.add(offre);
                }
            }
        }

        return offres;
    }

    public LocalDate trouverDebutDisponible(List<Reservation> reservations, LocalDate debut, LocalDate fin) {
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

    public boolean chevauche(Reservation reservation, LocalDate debut, LocalDate fin) {
        return !(reservation.fin().isBefore(debut) || reservation.debut().isAfter(fin));
    }

}
