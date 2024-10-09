package com.example.demo.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Dto.ReviewDTO;
import com.example.demo.Entities.Hotel;
import com.example.demo.Entities.Review;
import com.example.demo.Entities.User;
import com.example.demo.Exception.HotelNotFoundException;
import com.example.demo.Exception.NoContentFoundException;
import com.example.demo.Exception.ReviewNotFoundException;
import com.example.demo.Exception.UserNotFoundException;
import com.example.demo.Mapper.ReviewMapper;
import com.example.demo.dao.HotelRepository;
import com.example.demo.dao.ReviewRepository;
import com.example.demo.dao.UserRepository;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepo;

    @Autowired
    private HotelRepository hotelRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ReviewMapper reviewMapper;

    public ReviewDTO saveReview(ReviewDTO reviewDTO) {
        int hotelId = reviewDTO.getHotelId();  
        Long userId = reviewDTO.getUserId();

        Hotel hotel = hotelRepo.findById(hotelId).orElseThrow(
            () -> new HotelNotFoundException("Hotel with id " + hotelId + " not found.")
        );

        User user = userRepo.findById(userId).orElseThrow(
            () -> new UserNotFoundException("User with id " + userId + " not found.")
        );

        Review review = new Review();
        review.setReviewText(reviewDTO.getReviewText());
        review.setCleanliness(reviewDTO.getCleanlinessRating());
        review.setStaffService(reviewDTO.getStaffServiceRating());
        review.setAmenities(reviewDTO.getAmenitiesRating());
        review.setPropertyConditions(reviewDTO.getPropertyConditionsRating());
        review.setEcoFriendliness(reviewDTO.getEcoFriendlinessRating());
        review.setHotel(hotel);
        review.setUser(user);
        
        reviewDTO.calculateOverallRating(); 
        review.setOverallRating(reviewDTO.getOverallRating()); 
        
        review = reviewRepo.save(review);

        return reviewMapper.toDto(review);
    }

    public List<ReviewDTO> getHotelReviews(int hotelId) {
        Hotel hotel = hotelRepo.findById(hotelId).orElseThrow(
            () -> new HotelNotFoundException("Hotel with id " + hotelId + " not found.")
        );

        List<Review> reviews = reviewRepo.findByHotel(hotel);
        if (reviews.isEmpty()) {
            throw new NoContentFoundException("No reviews found for hotel id: " + hotelId);
        }
        return reviews.stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ReviewDTO> getAllUserGivenReviews(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(
            () -> new UserNotFoundException("User with id " + userId + " not found.")
        );

        List<Review> reviews = reviewRepo.findByUser(user);
        if (reviews.isEmpty()) {
            throw new NoContentFoundException("No reviews found for user id: " + userId);
        }
        return reviews.stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteReview(Long reviewId) {
        Review review = reviewRepo.findByReviewId(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review with id " + reviewId + " not found."));
        reviewRepo.delete(review);
    }
}
