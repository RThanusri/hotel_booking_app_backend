package com.example.demo.Mapper;

import com.example.demo.Dto.ReviewDTO;
import com.example.demo.Entities.Hotel;
import com.example.demo.Entities.Review;
import com.example.demo.Entities.User;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewDTO toDto(Review review) {
        if (review == null) {
            return null;
        }
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setReviewId(review.getReviewId());
        reviewDTO.setReviewText(review.getReviewText());
        reviewDTO.setCleanlinessRating(review.getCleanliness());
        reviewDTO.setStaffServiceRating(review.getStaffService());
        reviewDTO.setAmenitiesRating(review.getAmenities());
        reviewDTO.setPropertyConditionsRating(review.getPropertyConditions());
        reviewDTO.setEcoFriendlinessRating(review.getEcoFriendliness());
        reviewDTO.setHotelId(review.getHotel().getId());
        reviewDTO.setUserId(review.getUser().getUserId());
        reviewDTO.setOverallRating(review.getOverallRating());
        return reviewDTO;
    }

    public Review toEntity(ReviewDTO reviewDTO, Hotel hotel, User user) {
        if (reviewDTO == null) {
            return null;
        }
        Review review = new Review();
        review.setReviewText(reviewDTO.getReviewText());
        review.setCleanliness(reviewDTO.getCleanlinessRating());
        review.setStaffService(reviewDTO.getStaffServiceRating());
        review.setAmenities(reviewDTO.getAmenitiesRating());
        review.setPropertyConditions(reviewDTO.getPropertyConditionsRating());
        review.setEcoFriendliness(reviewDTO.getEcoFriendlinessRating());
        review.setOverallRating(reviewDTO.getOverallRating());
        review.setHotel(hotel);
        review.setUser(user);
        return review;
    }
}
