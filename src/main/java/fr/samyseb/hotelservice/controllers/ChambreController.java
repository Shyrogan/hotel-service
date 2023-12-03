package fr.samyseb.hotelservice.controllers;

import fr.samyseb.hotelservice.entities.Chambre;
import fr.samyseb.hotelservice.repositories.ChambreRepository;
import fr.samyseb.hotelservice.services.ChambreService;
import fr.samyseb.hotelservice.services.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return chambreRepository.findByHotel(hotelService.identity());
    }

    @GetMapping("/{numero}")
    @Transactional
    public Chambre getChambre(@PathVariable long numero) {
        return chambreRepository.findChambreByNumeroAndHotel(numero, hotelService.identity());
    }

    @GetMapping(value = "/img/{numero}", produces = MediaType.IMAGE_JPEG_VALUE)
    @Transactional
    public ResponseEntity<byte[]> getImgChambre(@PathVariable long numero) {
        byte[] imageData = chambreRepository.findChambreByNumeroAndHotel(numero, hotelService.identity()).image();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageData);
    }

}
