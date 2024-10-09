package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Hotel;
import com.example.demo.Entities.Review;
import com.example.demo.Entities.User;


@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {

	

	List<Review> findByHotel(Hotel hotel);
	List<Review>findByUser(User user);
	Optional<Review> findByReviewId(Long reviewId);
	
	
	

}
