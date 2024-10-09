package com.example.demo.dao;


	
	import java.util.List;

	import org.springframework.data.jpa.repository.JpaRepository;
	import org.springframework.data.jpa.repository.Query;
	import org.springframework.data.repository.query.Param;
	import org.springframework.stereotype.Repository;

import com.example.demo.Dto.HotelDTO;
import com.example.demo.Entities.Hotel;




	@Repository
	public interface HotelRepository extends JpaRepository<Hotel,Integer> {
		
		
		@Query(value = "SELECT * FROM hotel WHERE user_id = :userId and id =:id", nativeQuery = true)
	    Hotel findSpecificHotelByOwnerId(@Param ("userId")Long userId ,@Param ("id") int id);
		
		

		List<Hotel> findByAddressContainingIgnoreCase(String address);

		List<Hotel> findByAmenitiesContainingIgnoreCase(String amenities);
		@Query(value = "SELECT * FROM hotels WHERE user_id = :userId", nativeQuery = true)
	    List<Hotel> findHotelsByOwnerId(@Param("userId") Long userId);

	}


