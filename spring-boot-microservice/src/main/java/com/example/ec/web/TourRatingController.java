package com.example.ec.web;

import com.example.ec.domain.Tour;
import com.example.ec.domain.TourRating;
import com.example.ec.domain.TourRatingPk;
import com.example.ec.repo.TourRatingRepository;
import com.example.ec.repo.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

/**
 * Tour Rating Controller
 *
 * Created by Mary Ellen Bowman
 */
@RestController
@RequestMapping(path = "/tours/{tourId}/ratings")
public class TourRatingController {
    TourRatingRepository tourRatingRepository;
    TourRepository tourRepository;

    @Autowired
    public TourRatingController(TourRatingRepository tourRatingRepository, TourRepository tourRepository) {
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
    }

    protected TourRatingController() {

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Validated
    public TourRating createTourRating(@RequestBody @Valid RatingDto dto,
                                       @PathVariable(value="tourId") Integer tourId) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new NoSuchElementException("Tour not find with id " + tourId));

        TourRatingPk tourRatingPk = new TourRatingPk(tour, dto.getCustomerId());
        TourRating tourRating = new TourRating(tourRatingPk, dto.getScore(), dto.getComment());

        return tourRatingRepository.save(tourRating);
    }

    /**
     * Exception handler if NoSuchElementException is thrown
     *
     * @param ex exception
     * @return Error message String
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();
    }
}
