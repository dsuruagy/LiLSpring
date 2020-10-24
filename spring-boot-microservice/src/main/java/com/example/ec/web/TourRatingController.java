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
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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

        TourRatingPk tourRatingPk = new TourRatingPk(verifyTour(tourId), dto.getCustomerId());
        TourRating tourRating = new TourRating(tourRatingPk, dto.getScore(), dto.getComment());

        return tourRatingRepository.save(tourRating);
    }

    @PutMapping
    public RatingDto updateWithPut(@PathVariable(value = "tourId") Integer tourId,
                                   @RequestBody @Validated RatingDto dto) {
        TourRating tourRating = verifyTourRating(tourId, dto.getCustomerId());
        tourRating.setScore(dto.getScore());
        tourRating.setComment(dto.getComment());
        return new RatingDto(tourRatingRepository.save(tourRating));
    }

    @PatchMapping
    public RatingDto updateWithPatch(@PathVariable(value = "tourId") Integer tourId,
                                     @RequestBody @Validated RatingDto dto) {
        TourRating tourRating = verifyTourRating(tourId, dto.getCustomerId());
        if(dto.getScore() != null) {
            tourRating.setScore(dto.getScore());
        }
        if(dto.getComment() != null) {
            tourRating.setComment(dto.getComment());
        }
        return new RatingDto(tourRatingRepository.save(tourRating));
    }

    @DeleteMapping("/{customerId}")
    public void delete(@PathVariable(value = "tourId") Integer tourId,
                       @PathVariable(value = "customerId") Integer customerId) {
        TourRating tourRating = verifyTourRating(tourId, customerId);

        tourRatingRepository.delete(tourRating);
    }

    private Tour verifyTour(Integer tourId) {
        return tourRepository.findById(tourId)
                .orElseThrow(() -> new NoSuchElementException("Tour not find with id " + tourId));
    }

    private TourRating verifyTourRating(Integer tourId, Integer customerId) {
        return tourRatingRepository.findByPkTourIdAndPkCustomerId(tourId, customerId)
                .orElseThrow(() -> new NoSuchElementException(
                        MessageFormat.format("No Tour Rating find with id {0} and customer {1}",
                                tourId, customerId)));
    }

    @GetMapping
    public List<RatingDto> getAllRatingsForTour(@PathVariable(value = "tourId") int tourId) {
        verifyTour(tourId);
        return tourRatingRepository.findByPkTourId(tourId).stream()
                .map(RatingDto::new).collect(Collectors.toList());
    }

    @GetMapping("/average")
    public Map<String, Double> getAverage(@PathVariable(value = "tourId") int tourId){
        verifyTour(tourId);
        return Map.of("average", tourRatingRepository.findByPkTourId(tourId).stream()
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
