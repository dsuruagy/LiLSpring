package com.example.ec.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;
import java.util.Objects;

/**
 * The Tour contains all attributes of an Explore California Tour.
 *
 * Created by Mary Ellen Bowman
 */
@Document
public class Tour {
    @Id
    private String id;

    @Indexed
    private String title;

    @Indexed
    private String tourPackageCode;

    /**
     * We are replacing the TourPackage attribute for it, because we don't have
     * table relationships anymore. It is a duplication of code, but it is how NoSQL DB works.
     */
    private String tourPackageName;

    /**
     * Used for attributes like description, blurb, difficult, region, because they can change.
     */
    private Map<String, String> details;

    /**
     * Construct a fully initialized Tour
     * @param title title of the tour
     * @param tourPackage tour package
     * @param details details about the tour (key-value pairs)
     */
    public Tour(String title, TourPackage tourPackage, Map<String, String> details) {
        this.title = title;
        this.tourPackageCode = tourPackage.getCode();
        this.tourPackageName = tourPackage.getName();
        this.details = details;

    }

    protected Tour() {
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTourPackageCode() {
        return tourPackageCode;
    }

    public void setTourPackageCode(String tourPackageCode) {
        this.tourPackageCode = tourPackageCode;
    }

    public String getTourPackageName() {
        return tourPackageName;
    }

    public void setTourPackageName(String tourPackageName) {
        this.tourPackageName = tourPackageName;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Tour{" +
                "id='" + id + '\'' +
                ", details=" + details +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tour tour = (Tour) o;
        return id.equals(tour.id) &&
                title.equals(tour.title) &&
                tourPackageCode.equals(tour.tourPackageCode) &&
                tourPackageName.equals(tour.tourPackageName) &&
                details.equals(tour.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, tourPackageCode, tourPackageName, details);
    }
}
