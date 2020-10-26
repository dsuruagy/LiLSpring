package com.example.ec.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Rating of a Tour by a Customer
 *
 * Created by Mary Ellen Bowman
 *
 * Because this is a simple object, we don't need a DTO anymore. So validation is being made here.
 */
@Document
public class TourRating {

    @Id
    private String id;

    private String tourId;

    @NotNull
    private Integer customerId;

    @Min(0)
    @Max(5)
    private Integer score;

    @Size(max = 255)
    private String comment;

    /**
     * Create a new Tour Rating.
     *
     * @param tourId     tour Identifier
     * @param customerId customer identifier
     * @param score      Integer score (1-5)
     * @param comment    Optional comment from the customer
     */
    public TourRating(String tourId, Integer customerId, Integer score, String comment) {
        this.tourId = tourId;
        this.customerId = customerId;
        this.score = score;
        this.comment = comment;
    }

    protected TourRating() {
    }

    @Override
    public String toString() {
        return "TourRating{" +
                "id='" + id + '\'' +
                ", tourId='" + tourId + '\'' +
                ", customerId=" + customerId +
                ", score=" + score +
                ", comment='" + comment + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TourRating that = (TourRating) o;
        return id.equals(that.id) &&
                tourId.equals(that.tourId) &&
                customerId.equals(that.customerId) &&
                score.equals(that.score) &&
                comment.equals(that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tourId, customerId, score, comment);
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Integer getScore() {
        return score;
    }

    public String getComment() {
        return comment;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
