package com.example.ec.web;

import com.example.ec.domain.Tour;
import com.example.ec.domain.TourRating;
import com.example.ec.repo.TourRatingRepository;
import com.example.ec.repo.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.Map;
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
    public TourRating createTourRating(@RequestBody @Validated TourRating tourRating,
                                       @PathVariable(value="tourId") String tourId) {
        verifyTour(tourId);
        return tourRatingRepository.save(new TourRating(tourId, tourRating.getCustomerId(),
                tourRating.getScore(), tourRating.getComment()));
    }

    @PutMapping
    public TourRating updateWithPut(@PathVariable(value = "tourId") String tourId,
                                   @RequestBody @Validated TourRating tourRating) {
        TourRating rating = verifyTourRating(tourId, tourRating.getCustomerId());
        rating.setScore(tourRating.getScore());
        rating.setComment(tourRating.getComment());
        return tourRatingRepository.save(rating);
    }

    @PatchMapping
    public TourRating updateWithPatch(@PathVariable(value = "tourId") String tourId,
                                     @RequestBody @Validated TourRating tourRating) {
        TourRating rating = verifyTourRating(tourId, tourRating.getCustomerId());
        if(tourRating.getScore() != null) {
            rating.setScore(tourRating.getScore());
        }
        if(tourRating.getComment() != null) {
            rating.setComment(tourRating.getComment());
        }
        return tourRatingRepository.save(rating);
    }

    @DeleteMapping("/{customerId}")
    public void delete(@PathVariable(value = "tourId") String tourId,
                       @PathVariable(value = "customerId") Integer customerId) {
        TourRating tourRating = verifyTourRating(tourId, customerId);

        tourRatingRepository.delete(tourRating);
    }

    private Tour verifyTour(String tourId) {
        return tourRepository.findById(tourId)
                .orElseThrow(() -> new NoSuchElementException("Tour not find with id " + tourId));
    }

    private TourRating verifyTourRating(String tourId, Integer customerId) {
        return tourRatingRepository.findByTourIdAndCustomerId(tourId, customerId)
                .orElseThrow(() -> new NoSuchElementException(
                        MessageFormat.format("No Tour Rating find with id {0} and customer {1}",
                                tourId, customerId)));
    }

    @GetMapping
    public Page<TourRating> getRatings(@PathVariable(value = "tourId") String tourId,
                                                Pageable pageable) {
        return tourRatingRepository.findByTourId(tourId, pageable);
    }

    @GetMapping("/average")
    public Map<String, Double> getAverage(@PathVariable(value = "tourId") String tourId){
        verifyTour(tourId);
        return Map.of("average", tourRatingRepository.findByTourId(tourId).stream()
                .mapToInt(TourRating::getScore).average()
                .orElseThrow(() ->
                        new NoSuchElementException("Tour has no ratings")));
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
