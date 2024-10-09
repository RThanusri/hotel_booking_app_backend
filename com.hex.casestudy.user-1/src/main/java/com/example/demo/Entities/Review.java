package com.example.demo.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private String reviewText;
    private int cleanliness;
    private int staffService;
    private int amenities;
    private int propertyConditions;
    private int ecoFriendliness;
    private int overallRating;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Review() {
    }

    public Review(Long reviewId, String reviewText, int cleanliness, int staffService,
                  int amenities, int propertyConditions, int ecoFriendliness, int overallRating, Hotel hotel, User user) {
        this.reviewId = reviewId;
        this.reviewText = reviewText;
        this.cleanliness = cleanliness;
        this.staffService = staffService;
        this.amenities = amenities;
        this.propertyConditions = propertyConditions;
        this.ecoFriendliness = ecoFriendliness;
        this.overallRating = overallRating; 
        this.hotel = hotel;
        this.user = user;
    }

    public double calculateOverallRating() {
        return (cleanliness + staffService + amenities + propertyConditions + ecoFriendliness) / 5.0; 
    }

    public int getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(int overallRating) {
        this.overallRating = overallRating;
    }

    @Override
    public String toString() {
        return "Review [reviewId=" + reviewId + ", reviewText='" + reviewText + '\'' +
                ", cleanliness=" + cleanliness + ", staffService=" + staffService +
                ", amenities=" + amenities + ", propertyConditions=" + propertyConditions +
                ", ecoFriendliness=" + ecoFriendliness + ", overallRating=" + calculateOverallRating() +
                ", hotel=" + hotel + ", user=" + user + "]";
    }

	public Long getReviewId() {
		return reviewId;
	}

	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}

	public String getReviewText() {
		return reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	public int getCleanliness() {
		return cleanliness;
	}

	public void setCleanliness(int cleanliness) {
		this.cleanliness = cleanliness;
	}

	public int getStaffService() {
		return staffService;
	}

	public void setStaffService(int staffService) {
		this.staffService = staffService;
	}

	public int getAmenities() {
		return amenities;
	}

	public void setAmenities(int amenities) {
		this.amenities = amenities;
	}

	public int getPropertyConditions() {
		return propertyConditions;
	}

	public void setPropertyConditions(int propertyConditions) {
		this.propertyConditions = propertyConditions;
	}

	public int getEcoFriendliness() {
		return ecoFriendliness;
	}

	public void setEcoFriendliness(int ecoFriendliness) {
		this.ecoFriendliness = ecoFriendliness;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
}
