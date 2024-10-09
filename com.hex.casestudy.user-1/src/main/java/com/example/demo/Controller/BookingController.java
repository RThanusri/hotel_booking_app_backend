package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Dto.BookingCreateDTO;
import com.example.demo.Dto.BookingResponseDTO;
import com.example.demo.Dto.BookingUpdateDTO;
import com.example.demo.Dto.RoomDTO;
import com.example.demo.Entities.Booking;
import com.example.demo.Enum.Role;
import com.example.demo.Service.BookingService;
import com.example.demo.Service.RoomService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomService roomService;
  
    @PostMapping("/api/user/makeBooking")
    public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody BookingCreateDTO bookingCreateDTO) {
        BookingResponseDTO bookingResponse = bookingService.createBooking(bookingCreateDTO);
        return ResponseEntity.ok(bookingResponse);
    }

    @PutMapping("/api/user/updatebookings/{bookingId}")
    public ResponseEntity<String> updateBooking(@PathVariable long bookingId, @RequestBody BookingUpdateDTO bookingUpdateDTO) {
        bookingService.updateBooking(bookingId, bookingUpdateDTO);
        return ResponseEntity.ok("Booking Details Updated Successfully");
    }

    @GetMapping("/api/shared/getBookingById/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable long bookingId) {
        Booking booking = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(booking);
    }

    @DeleteMapping("/api/shared/cancelBookings/{bookingId}")
    public ResponseEntity<String> cancelBooking(@PathVariable long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok("Booking Cancelled Successfully");
    }

    @GetMapping("/api/shared/rooms/available")
    public ResponseEntity<List<RoomDTO>> getAvailableRooms(
            @RequestParam LocalDate checkInDate,
            @RequestParam LocalDate checkOutDate,
            @RequestParam int numberOfAdults,
            @RequestParam int numberOfChildren,
            @RequestParam int hotelId) {
        List<RoomDTO> rooms = roomService.getAvailableRooms(checkInDate, checkOutDate, numberOfAdults, numberOfChildren, hotelId);
        return ResponseEntity.ok(rooms);
    }
    @GetMapping("/api/shared/checkAvailability")
    public ResponseEntity<Boolean> checkAvailability(
            @RequestParam Long roomId,
            @RequestParam LocalDate checkInDate,
            @RequestParam LocalDate checkOutDate) {
        
        boolean isAvailable = bookingService.isRoomAvailable(roomId, checkInDate, checkOutDate);

        return ResponseEntity.ok(isAvailable);
    }
    
    @GetMapping("/api/owner/getBookingsForOwner/{userId}")
    public ResponseEntity<List<Booking>> getBookingsForOwner(@PathVariable Long userId) {
    	System.out.println("controller");
        List<Booking> bookings = bookingService.getBookingsByOwnerId(userId);
        System.out.println("bookings :"+bookings);
        return ResponseEntity.ok(bookings);
    }
    
    @GetMapping("/api/shared/getBookingsByUserId/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUserId(@PathVariable long userId) {
        List<Booking> bookings = bookingService.getBookingsByUser(userId);
        return ResponseEntity.ok(bookings);
    }
    
    @GetMapping("/api/admin/getAllBookings")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }
}