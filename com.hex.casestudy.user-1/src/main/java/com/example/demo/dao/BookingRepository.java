package com.example.demo.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Booking;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {


	

    List<Booking> findByRooms_IdAndCheckInDateBeforeAndCheckOutDateAfter(Long roomId, LocalDate checkOutDate, LocalDate checkInDate);

    List<Booking> findByUser_UserId(Long userId);
    @Query("SELECT DISTINCT b FROM Booking b JOIN b.rooms r JOIN r.hotel h WHERE h.user.userId = :userId")
    List<Booking> findAllByOwnerId(@Param("userId") Long userId);


}
