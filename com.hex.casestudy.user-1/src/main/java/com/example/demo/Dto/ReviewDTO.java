package com.example.demo.Dto;

public class ReviewDTO {
    private Long reviewId;
    private int overallRating;
    private String reviewText;
    private int cleanlinessRating;
    private int staffServiceRating;
    private int amenitiesRating;
    private int propertyConditionsRating;
    private int ecoFriendlinessRating;
    private int hotelId;  
    private Long userId;   

    public ReviewDTO() {
    }

    public ReviewDTO(Long reviewId, int overallRating, String reviewText, int cleanlinessRating, 
                     int staffServiceRating, int amenitiesRating, int propertyConditionsRating,
                     int ecoFriendlinessRating, int hotelId, Long userId) {
        this.reviewId = reviewId;
        this.overallRating = overallRating;
        this.reviewText = reviewText;
        this.cleanlinessRating = cleanlinessRating;
        this.staffServiceRating = staffServiceRating;
        this.amenitiesRating = amenitiesRating;
        this.propertyConditionsRating = propertyConditionsRating;
        this.ecoFriendlinessRating = ecoFriendlinessRating;
        this.hotelId = hotelId;
        this.userId = userId;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public int getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(int overallRating) {
        this.overallRating = overallRating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public int getCleanlinessRating() {
        return cleanlinessRating;
    }

    public void setCleanlinessRating(int cleanlinessRating) {
        this.cleanlinessRating = cleanlinessRating;
    }

    public int getStaffServiceRating() {
        return staffServiceRating;
    }

    public void setStaffServiceRating(int staffServiceRating) {
        this.staffServiceRating = staffServiceRating;
    }

    public int getAmenitiesRating() {
        return amenitiesRating;
    }

    public void setAmenitiesRating(int amenitiesRating) {
        this.amenitiesRating = amenitiesRating;
    }

    public int getPropertyConditionsRating() {
        return propertyConditionsRating;
    }

    public void setPropertyConditionsRating(int propertyConditionsRating) {
        this.propertyConditionsRating = propertyConditionsRating;
    }

    public int getEcoFriendlinessRating() {
        return ecoFriendlinessRating;
    }

    public void setEcoFriendlinessRating(int ecoFriendlinessRating) {
        this.ecoFriendlinessRating = ecoFriendlinessRating;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void calculateOverallRating() {
        this.overallRating = (cleanlinessRating + staffServiceRating + amenitiesRating +
                              propertyConditionsRating + ecoFriendlinessRating) / 5;
    }

    @Override
    public String toString() {
        return "ReviewDTO [reviewId=" + reviewId + ", overallRating=" + overallRating + ", reviewText='" + reviewText + '\'' +
               ", cleanlinessRating=" + cleanlinessRating + ", staffServiceRating=" + staffServiceRating +
               ", amenitiesRating=" + amenitiesRating + ", propertyConditionsRating=" + propertyConditionsRating +
               ", ecoFriendlinessRating=" + ecoFriendlinessRating + ", hotelId=" + hotelId + ", userId=" + userId + "]";
    }
}
