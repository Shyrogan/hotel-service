package fr.samyseb.hotelservice.controllers;

import fr.samyseb.hotelservice.entities.Hotel;
import fr.samyseb.hotelservice.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HotelController {

    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/")
    public Hotel getHotelMetadata() {
        return hotelService.identity();
    }

}
