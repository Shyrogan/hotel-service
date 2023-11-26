package fr.samyseb.hotelservice.controllers;

import fr.samyseb.hotelservice.entities.Chambre;
import fr.samyseb.hotelservice.repositories.ChambreRepository;
import fr.samyseb.hotelservice.services.ChambreService;
import fr.samyseb.hotelservice.services.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


import java.util.List;

@RestController
@RequestMapping("/chambre")
@RequiredArgsConstructor
public class ChambreController {

    private final HotelService hotelService;
    private final ChambreRepository chambreRepository;
    private final ChambreService chambreService;

    @GetMapping("/")
    @Transactional
    public List<Chambre> getChambres() {
        return chambreRepository.findByHotelId(hotelService.identity().id());
    }

    @GetMapping("/{numero}")
    @Transactional
    public Chambre getChambre(@PathVariable long numero) {
        return chambreRepository.findChambreByNumeroAndHotel_Id(numero, hotelService.identity().id());
    }

    @GetMapping(value = "/img/{numero}", produces = MediaType.IMAGE_JPEG_VALUE)
    @Transactional
    public ResponseEntity<byte[]> getImgChambre(@PathVariable long numero) {
        byte[] imageData = chambreRepository.findChambreByNumeroAndHotel_Id(numero, hotelService.identity().id()).image();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageData);
    }

    /*
        @GetMapping("/img/{numero}")
    @Transactional
    public byte[] getImgChambre(@PathVariable long numero) {
        return chambreRepository.findChambreByNumeroAndHotel_Id(numero, hotelService.identity().id()).image();

    }
     */
}
