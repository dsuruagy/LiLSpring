package com.dsuruagy.lil.learningspring.web;

import java.time.LocalDate;
import java.util.List;

import com.dsuruagy.lil.learningspring.business.domain.RoomReservation;
import com.dsuruagy.lil.learningspring.business.service.ReservationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reservations")
public class RoomReservationWebController {
    private final ReservationService reservationService;

    private static Logger logger = 
    LoggerFactory.getLogger(RoomReservationWebController.class.getName());

    @Autowired
    public RoomReservationWebController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public String getReservations
        (@RequestParam(value = "date", 
        required = false) String stringDate, Model model) {
        
        logger.info("stringDate: " + stringDate);

        LocalDate date = (stringDate!=null) ? LocalDate.parse(stringDate) :
                        LocalDate.now();

        List<RoomReservation> roomReservations = reservationService
            .getReservationsForDate(date);

        model.addAttribute("roomReservations", roomReservations);

        return "reservations";
    }
    
}
