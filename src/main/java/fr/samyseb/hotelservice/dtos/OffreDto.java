package fr.samyseb.hotelservice.dtos;

import fr.samyseb.hotelservice.pojos.Offre;

import java.time.LocalDate;

public class OffreDto {

    private HotelDto hotel;
    private float prixSejour;
    private ChambreDto chambre;
    private LocalDate debut;
    private LocalDate fin;

    public HotelDto getHotel() {
        return hotel;
    }

    public float getPrixSejour() {
        return prixSejour;
    }

    public ChambreDto getChambre() {
        return chambre;
    }

    public LocalDate getDebut() {
        return debut;
    }

    public LocalDate getFin() {
        return fin;
    }

    public Offre toEntity() {
        return Offre.builder()
                .hotel(hotel.toEntity())
                .prixSejour(prixSejour)
                .chambre(chambre.toEntity())
                .debut(debut)
                .fin(fin)
                .build();
    }

}
