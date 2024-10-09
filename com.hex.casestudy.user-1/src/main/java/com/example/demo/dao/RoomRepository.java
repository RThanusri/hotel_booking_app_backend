package com.example.demo.dao;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

	 

	List<Room> findByHotelId(int hotelId);

	
	 @Query("SELECT r FROM Room r WHERE r.hotel.id = :hotelId " +
	           "AND r.availableFrom <= :checkOutDate " +
	           "AND r.availableTo >= :checkInDate")
	    List<Room> findAvailableRooms(@Param("checkInDate") LocalDate checkInDate,
	                                  @Param("checkOutDate") LocalDate checkOutDate,
	                                  @Param("hotelId") int hotelId);


	List<Room> findByAvailableFromLessThanEqualAndAvailableToGreaterThanEqual(LocalDate checkInDate,
			LocalDate checkOutDate);


	List<Room> findByHotel_AddressContainingAndAvailableFromLessThanEqualAndAvailableToGreaterThanEqualAndMaxOccupancyGreaterThanEqual(
			String address, LocalDate checkInDate, LocalDate checkOutDate, int minOccupancy);


	
	    List<Room> findByHotelId(Long hotelId);
	}

	    
	





	

	    
	    





