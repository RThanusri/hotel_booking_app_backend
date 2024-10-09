package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dto.ReviewDTO;

import com.example.demo.Service.ReviewService;

@RestController

public class ReviewController {

    @Autowired
    private ReviewService reviewSer;

    @PostMapping("/api/user/submitReview")
    public ResponseEntity<String> saveReview(@RequestBody ReviewDTO reviewDTO) {
        reviewSer.saveReview(reviewDTO);
        return ResponseEntity.ok("Review Submitted Successfully");
    }

    @GetMapping("/api/shared/hotelReviews/{hotelId}")
    public ResponseEntity<List<ReviewDTO>> getHotelReview(@PathVariable int hotelId) {
        List<ReviewDTO> reviews = reviewSer.getHotelReviews(hotelId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/api/user/userReviews/{userId}")
    public ResponseEntity<List<ReviewDTO>> getAllUserGivenReviews(@PathVariable Long userId) {
        List<ReviewDTO> reviews = reviewSer.getAllUserGivenReviews(userId);
        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("api/user/deleteReview/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        reviewSer.deleteReview(reviewId);
        return ResponseEntity.ok("Review Deleted Successfully");
    }
}
