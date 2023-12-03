package fr.samyseb.hotelservice.services;

import fr.samyseb.hotelservice.entities.Chambre;
import fr.samyseb.hotelservice.entities.Hotel;
import fr.samyseb.hotelservice.repositories.ChambreRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@DependsOn("hotelService")
@RequiredArgsConstructor
public class ChambreService {

    private final HotelService hotelService;
    private final ChambreRepository chambreRepository;
    private final Environment environment;

    @PostConstruct
    public void initChambres() throws IOException {
        int initialChambres = environment.getProperty("hotel.initialChambres", Integer.class, 8);
        Random random = new Random();
        Hotel hotel = hotelService.identity(); // Obtenez l'instance de l'hôtel

        // Liste des noms de fichiers d'images
        List<byte[]> imageNames = Stream.of("0935_ho_00_p_1024x768.jpg",
                "101525.jpg",
                "6257f19fbb069d0e7b8a65e9.jpg",
                "bedroom-1285156_1280.jpg",
                "bedroom-3475656_1280.jpg",
                "bedroom-490779_1280.jpg",
                "bedroom-8004697_1280.jpg",
                "chambre-double-premium-slider002-min.jpg",
                "Chambre_TOOHotel_Vue_Seine-2.jpg-2-1024x683.jpg",
                "duplex-parisien.jpg",
                "hotel-room-1447201_1280.jpg",
                "hotel-room-1505455_1280.jpg",
                "hotel-room-2619509_1280.jpg",
                "lapland-4688326_1280.jpg",
                "to-travel-1677347_1280.jpg",
                "upholstery-4809588_1280.jpg"
        ).map(s -> "/imgChambres/" + s).map(this::readImageAsBytes).toList();


        // Vérifier si suffisamment d'images sont disponibles
        if (imageNames.size() < initialChambres) {
            throw new IllegalStateException("Pas assez d'images pour toutes les chambres");
        }

        for (int i = 0; i < initialChambres; i++) {
            float prix = 50.0f + random.nextFloat() * (450.0f); // Prix aléatoire entre 50 et 500
            int places = 2 + random.nextInt(4); // Nombre de places aléatoire entre 2 et 5

            // Sélectionnez une image aléatoire pour chaque chambre
            int imageIndex = random.nextInt(imageNames.size());
            chambreRepository.save(Chambre.builder()
                    .id(UUID.randomUUID())
                    .numero(i + 100)
                    .prix(prix)
                    .places(places)
                    .hotel(hotel)
                    .image(imageNames.get(imageIndex))  // Attribuer l'image à la chambre
                    .build());
        }
    }

    @PreDestroy
    public void removeChambres() {
        chambreRepository.deleteAllByHotel(hotelService.identity());
    }

    public byte[] readImageAsBytes(String imagePath) {
        try (InputStream is = getClass().getResourceAsStream(imagePath)) {
            if (is == null) {
                throw new FileNotFoundException("Resource not found: " + imagePath);
            }
            return is.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
