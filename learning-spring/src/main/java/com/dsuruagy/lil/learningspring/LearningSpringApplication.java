package com.dsuruagy.lil.learningspring;

import com.dsuruagy.lil.learningspring.data.entity.Guest;
import com.dsuruagy.lil.learningspring.data.entity.Reservation;
import com.dsuruagy.lil.learningspring.data.entity.Room;
import com.dsuruagy.lil.learningspring.data.repository.GuestRepository;
import com.dsuruagy.lil.learningspring.data.repository.ReservationRepository;
import com.dsuruagy.lil.learningspring.data.repository.RoomRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class LearningSpringApplication {
	private static Logger logger = 
		LoggerFactory.getLogger(LearningSpringApplication.class.getName());

	public static void main(String[] args) {
		logger.info("ENCODING: " + System.getProperty("file.encoding"));
		SpringApplication.run(LearningSpringApplication.class, args);
	}

	@RestController
	@RequestMapping("/rooms")
	public class RoomController {

		@Autowired
		private RoomRepository roomRepository;

		@GetMapping
		public Iterable<Room> getRooms() {
			Iterable<Room> rooms = this.roomRepository.findAll();
			return rooms;
		}
	}

	@RestController
	@RequestMapping("/guests")
	public class GuestController {
		@Autowired
		private GuestRepository guestRepository;

		@GetMapping
		public Iterable<Guest> getGuests() {
			Iterable<Guest> guests = this.guestRepository.findAll();

			return guests;
		}
	}

	@RestController
	@RequestMapping("/reservations")
	public class ReservationController {
		@Autowired
		private ReservationRepository reservationRepository;

		@GetMapping
		public Iterable<Reservation> getReservations() {
			Iterable<Reservation> reservations = this.reservationRepository.findAll();

			return reservations;
		}
	}
}
