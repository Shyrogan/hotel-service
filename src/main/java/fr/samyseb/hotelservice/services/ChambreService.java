package fr.samyseb.hotelservice.services;

import fr.samyseb.hotelservice.entities.Chambre;
import fr.samyseb.hotelservice.entities.Hotel;
import fr.samyseb.hotelservice.repositories.ChambreRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ChambreService {


    private final HotelService hotelService;
    private final ChambreRepository chambreRepository;
    private final Environment environment;


    private List<Chambre> chambres = new ArrayList<>();

    @PostConstruct
    public void initChambres() {
        int initialChambres = environment.getProperty("hotel.initialChambres", Integer.class);
        Random random = new Random();
        Hotel hotel = hotelService.identity(); // Obtenez l'instance de l'hôtel
        for (int i = 0; i < initialChambres; i++) {

            float prix = 50.0f + random.nextFloat() * (450.0f); // Prix aléatoire entre 50 et 500
            int places = random.nextInt(15) + 1; // Nombre de places aléatoire entre 1 et 15

            // Augmente la probabilité d'avoir un nombre entre 2 et 5
            if (places < 2 || places > 5) {
                if (random.nextFloat() < 0.7) { // 70% de chance de refaire le tirage
                    places = 2 + random.nextInt(4);
                }
            }

            Chambre chambre = Chambre.builder()
                    .numero(i + 100)
                    .prix(prix)
                    .places(places)
                    .hotel(hotel)
                    .build();
            chambreRepository.save(chambre);
            chambres.add(chambre);
        }
    }

    @PreDestroy
    public void cleanupChambres() {
        for (Chambre chambre : chambres) {
            chambreRepository.delete(chambre);
        }
    }
}
