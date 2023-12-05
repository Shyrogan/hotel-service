package fr.samyseb.hotelservice.services;

import fr.samyseb.hotelservice.HotelApplication;
import fr.samyseb.hotelservice.entities.Adresse;
import fr.samyseb.hotelservice.entities.Hotel;
import fr.samyseb.hotelservice.repositories.AdresseRepository;
import fr.samyseb.hotelservice.repositories.HotelRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class HotelService {

    private static final Logger logger = LoggerFactory.getLogger(HotelApplication.class);

    private final Environment environment;
    private final AdresseRepository adresseRepository;
    private final HotelRepository hotelRepository;

    @Getter
    private Hotel identity;

    /**
     * Lors du démarrage de l'application, on récupère l'identité de l'hôtel dans l'environnement,
     * l'insère dans la base de donnée et l'assigne dans une variable.
     */
    @PostConstruct
    public void onStartup() throws MalformedURLException {
        identity = hotelRepository.save(Hotel.builder()
                .nom(environment.getRequiredProperty("hotel.name"))
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

}
