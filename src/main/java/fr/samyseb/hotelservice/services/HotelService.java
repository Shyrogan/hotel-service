package fr.samyseb.hotelservice.services;

import fr.samyseb.hotelservice.HotelApplication;
import fr.samyseb.hotelservice.entities.Adresse;
import fr.samyseb.hotelservice.entities.Hotel;
import fr.samyseb.hotelservice.repositories.AdresseRepository;
import fr.samyseb.hotelservice.repositories.HotelRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class HotelService {

    private static final Logger logger = LoggerFactory.getLogger(HotelApplication.class);
    private final Environment environment;
    private final AdresseRepository adresseRepository;
    private final HotelRepository hotelRepository;
    private Hotel identity;

    @Autowired
    public HotelService(Environment environment, AdresseRepository adresseRepository, HotelRepository repository) {
        this.environment = environment;
        this.adresseRepository = adresseRepository;
        this.hotelRepository = repository;
    }

    /**
     * Lors du démarrage de l'application, on récupère l'identité de l'hôtel dans l'environnement
     * et le stocke dans une variable
     */
    @PostConstruct
    public void onStartup() {
        identity = hotelRepository.save(Hotel.builder()
                .name(environment.getRequiredProperty("hotel.name"))
                .etoiles(environment.getRequiredProperty("hotel.etoiles", Integer.class))
                .adresse(adresseRepository.save(Adresse.builder()
                        .numero(environment.getRequiredProperty("hotel.adresse.numero"))
                        .rue(environment.getRequiredProperty("hotel.adresse.rue"))
                        .ville(environment.getRequiredProperty("hotel.adresse.ville"))
                        .pays(environment.getRequiredProperty("hotel.adresse.pays"))
                        .build()))
                .chambres(emptyList())
                .build());

        logger.info("L'identité de l'hôtel à été définie à: %s.".formatted(identity()));
    }

    @PreDestroy
    public void onShutdown() {
        hotelRepository.delete(identity);
        logger.info("Suppression de l'hôtel dans la liste des hôtels effectuée.");
    }

    /**
     * Retourne l'identité (si elle existe) de l'application hôtel.
     *
     * @return l'identité
     */
    public Hotel identity() {
        return this.identity;
    }

}
