package fr.samyseb.hotelservice.services;

import fr.samyseb.hotelservice.HotelApplication;
import fr.samyseb.hotelservice.entities.Adresse;
import fr.samyseb.hotelservice.entities.Hotel;
import fr.samyseb.hotelservice.repositories.AdresseRepository;
import fr.samyseb.hotelservice.repositories.HotelRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

import static java.util.Collections.emptyList;

@Service
public class HotelService {

    private static final Logger logger = LoggerFactory.getLogger(HotelApplication.class);

    private final Environment environment;
    private final AdresseRepository adresseRepository;
    private final HotelRepository hotelRepository;

    @Getter
    private Hotel identity;

    @Autowired
    public HotelService(Environment environment, AdresseRepository adresseRepository, HotelRepository repository) {
        this.environment = environment;
        this.adresseRepository = adresseRepository;
        this.hotelRepository = repository;
    }

    /**
     * Lors du démarrage de l'application, on récupère l'identité de l'hôtel dans l'environnement,
     * l'insère dans la base de donnée et l'assigne dans une variable.
     */
    @PostConstruct
    public void onStartup() throws MalformedURLException {
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
                .url(new URL("http", environment.getProperty("server.address"),
                        environment.getRequiredProperty("server.port", Integer.class),
                        ""))
                .build());

        logger.info("L'identité de l'hôtel à été définie à: %s.".formatted(identity()));
    }

    /**
     * Supprime l'identité de la base de donnée
     */
    @PreDestroy
    public void onShutdown() {
        hotelRepository.delete(identity);
        logger.info("Suppression de l'hôtel dans la liste des hôtels effectuée.");
    }

}
