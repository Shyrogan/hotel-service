package fr.samyseb.hotelservice.services;

import fr.samyseb.hotelservice.entities.Chambre;
import fr.samyseb.hotelservice.entities.Hotel;
import fr.samyseb.hotelservice.repositories.ChambreRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
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
    public void initChambres() throws IOException {
        int initialChambres = environment.getProperty("hotel.initialChambres", Integer.class, 8);
        Random random = new Random();
        Hotel hotel = hotelService.identity(); // Obtenez l'instance de l'hôtel

        // Liste des noms de fichiers d'images
        List<String> imageNames = new ArrayList<>(Arrays.asList(
                "0935_ho_00_p_1024x768.jpg",
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
        ));


        // Vérifier si suffisamment d'images sont disponibles
        if (imageNames.size() < initialChambres) {
            throw new IllegalStateException("Pas assez d'images pour toutes les chambres");
        }

        for (int i = 0; i < initialChambres; i++) {
            float prix = 50.0f + random.nextFloat() * (450.0f); // Prix aléatoire entre 50 et 500
            int places = 2 + random.nextInt(4); // Nombre de places aléatoire entre 2 et 5

            // Sélectionnez une image aléatoire pour chaque chambre
            int imageIndex = random.nextInt(imageNames.size());
            String imageName = imageNames.remove(imageIndex); // Retirez le nom pour éviter les duplications
            String imagePath = "/imgChambres/" + imageName;
            byte[] imageBytes = readImageAsBytes(imagePath);

            Chambre chambre = Chambre.builder()
                    .numero(i + 100)
                    .prix(prix)
                    .places(places)
                    .hotel(hotel)
                    .image(imageBytes)  // Attribuer l'image à la chambre
                    .build();
            chambreRepository.save(chambre);
            chambres.add(chambre);
        }
    }

    public byte[] readImageAsBytes(String imagePath) throws IOException {
        try (InputStream is = getClass().getResourceAsStream(imagePath)) {
            if (is == null) {
                throw new FileNotFoundException("Resource not found: " + imagePath);
            }
            return is.readAllBytes();
        }
    }


    @PreDestroy
    public void cleanupChambres() {
        for (Chambre chambre : chambres) {
            chambreRepository.delete(chambre);
        }
    }


}
