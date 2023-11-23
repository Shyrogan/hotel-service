package fr.samyseb.hotelservice.controllers;

import fr.samyseb.hotelservice.entities.Hotel;
import fr.samyseb.hotelservice.services.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping("/")
    public Hotel getHotelMetadata() {
        return hotelService.identity();
    }

}
