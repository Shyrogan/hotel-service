package fr.samyseb.hotelservice.services;

import fr.samyseb.hotelservice.HotelApplication;
import fr.samyseb.hotelservice.entities.Adresse;
import fr.samyseb.hotelservice.entities.Hotel;
import fr.samyseb.hotelservice.repositories.*;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

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
    private final ChambreRepository chambreRepository;
    private final ReservationRepository reservationRepository;
    private final PartenariatRepository partenariatRepository;
    private final TransactionTemplate transactionTemplate;

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

    @PreDestroy
    public void deleteAllHotelInformation() {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    reservationRepository.deleteAllByHotel(identity);
                    chambreRepository.deleteAllByHotel(identity);
                    partenariatRepository.deleteAllByHotel(identity);
                    hotelRepository.delete(identity);
                    adresseRepository.delete(identity.adresse());
                    logger.info("Suppression de l'hôtel et des données associées dans la liste des hôtels effectuée.");
                } catch (Exception e) {
                    logger.error("Erreur lors de la suppression des données de l'hôtel: ", e);
                    status.setRollbackOnly();
                }
            }
        });
    }

}
