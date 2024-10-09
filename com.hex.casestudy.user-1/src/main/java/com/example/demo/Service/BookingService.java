package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Dto.BookingCreateDTO;
import com.example.demo.Dto.BookingDTO;
import com.example.demo.Dto.BookingResponseDTO;
import com.example.demo.Dto.BookingUpdateDTO;
import com.example.demo.Entities.Booking;
import com.example.demo.Entities.BookingStatus;
import com.example.demo.Entities.Room;
import com.example.demo.Entities.User;
import com.example.demo.Exception.BookingIdNotFoundException;
import com.example.demo.Exception.RoomNotFoundException;
import com.example.demo.Mapper.BookingMapper;
import com.example.demo.dao.BookingRepository;
import com.example.demo.dao.RoomRepository;
import com.example.demo.dao.UserRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingMapper bookingMapper;

    public BookingResponseDTO createBooking(BookingCreateDTO bookingDTO) {
        validateBookingCreateDTO(bookingDTO);

        User user = userRepository.findById(bookingDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + bookingDTO.getUserId()));

        List<Room> rooms = new ArrayList<>();
        double totalFare = 0.0;
        List<List<Integer>> distributedGuests = distributeGuests(bookingDTO.getRoomIds(), bookingDTO.getGuestAges());

        for (int i = 0; i < bookingDTO.getRoomIds().size(); i++) {
            Long roomId = bookingDTO.getRoomIds().get(i);
            Room existingRoom = roomRepository.findById(roomId)
                    .orElseThrow(() -> new RoomNotFoundException("Room not found with ID: " + roomId));
            rooms.add(existingRoom);

            List<Integer> guests = distributedGuests.get(i);

            if (!isRoomAvailable(existingRoom.getId(), bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate())) {
                throw new IllegalArgumentException("Room with ID " + existingRoom.getId() + " is not available for the selected dates.");
            }

            double roomTotalFare = calculateTotalAmount(existingRoom, bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate(), guests);
            totalFare += roomTotalFare;

            updateRoomAvailability(existingRoom, bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate(), true);
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setCheckInDate(bookingDTO.getCheckInDate());
        booking.setCheckOutDate(bookingDTO.getCheckOutDate());
        booking.setNumberOfAdults(bookingDTO.getNumberOfAdults());
        booking.setNumberOfChildren(bookingDTO.getNumberOfChildren());
        booking.setGuestAges(bookingDTO.getGuestAges());
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setNumberOfRooms(bookingDTO.getNumberOfRooms());
        booking.setRooms(rooms);

        booking = bookingRepository.save(booking);

        BookingResponseDTO bookingResponse = new BookingResponseDTO();
        bookingResponse.setBookingId(booking.getBookingId());
        bookingResponse.setTotalFare(totalFare);
        bookingResponse.setBookingStatus(booking.getStatus().name());
        bookingResponse.setBookingDetails(bookingMapper.toDTO(booking));

        return bookingResponse;
    }

    public BookingResponseDTO updateBooking(Long bookingId, BookingUpdateDTO bookingDTO) {
        Booking existingBooking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingIdNotFoundException("Booking not found with ID: " + bookingId));

        List<Room> oldRooms = existingBooking.getRooms();
        for (Room room : oldRooms) {
            restoreRoomAvailability(room, existingBooking.getCheckInDate(), existingBooking.getCheckOutDate());
        }

        validateBookingUpdateDTO(bookingDTO);

        existingBooking.setCheckInDate(bookingDTO.getCheckInDate());
        existingBooking.setCheckOutDate(bookingDTO.getCheckOutDate());
        existingBooking.setNumberOfAdults(bookingDTO.getNumberOfAdults());
        existingBooking.setNumberOfChildren(bookingDTO.getNumberOfChildren());
        existingBooking.setStatus(BookingStatus.UPDATED);

        List<Room> updatedRooms = new ArrayList<>();
        double totalFare = 0.0;

        if (bookingDTO.getRoomIds() == null || bookingDTO.getRoomIds().isEmpty()) {
            throw new IllegalArgumentException("At least one room must be provided for the booking update.");
        }

        List<List<Integer>> distributedGuests = distributeGuests(bookingDTO.getRoomIds(), bookingDTO.getGuestAges());

        for (int i = 0; i < bookingDTO.getRoomIds().size(); i++) {
            Long roomId = bookingDTO.getRoomIds().get(i);
            Room existingRoom = roomRepository.findById(roomId)
                    .orElseThrow(() -> new RoomNotFoundException("Room not found with ID: " + roomId));
            updatedRooms.add(existingRoom);

            List<Integer> guests = distributedGuests.get(i);

            if (!isRoomAvailable(existingRoom.getId(), bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate())) {
                throw new IllegalArgumentException("Room with ID " + existingRoom.getId() + " is not available for the selected dates.");
            }

            double roomTotalFare = calculateTotalAmount(existingRoom, bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate(), guests);
            totalFare += roomTotalFare;
        }

        existingBooking.setRooms(updatedRooms);
        Booking updatedBooking = bookingRepository.save(existingBooking);

        for (Room room : updatedRooms) {
            updateRoomAvailability(room, bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate(), true);
        }

        BookingResponseDTO bookingResponse = new BookingResponseDTO();
        bookingResponse.setBookingId(updatedBooking.getBookingId());
        bookingResponse.setTotalFare(totalFare);
        bookingResponse.setBookingStatus(updatedBooking.getStatus().name());
        bookingResponse.setBookingDetails(bookingMapper.toDTO(updatedBooking));

        return bookingResponse;
    }

    public Booking getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingIdNotFoundException("Booking not found with ID: " + bookingId));
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingIdNotFoundException("Booking not found with ID: " + bookingId));

        List<Room> rooms = booking.getRooms();
        for (Room room : rooms) {
            restoreRoomAvailability(room, booking.getCheckInDate(), booking.getCheckOutDate());
        }

        booking.setStatus(BookingStatus.CANCELED);
        bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findByUser_UserId(userId);
    }
    public List<Booking> getBookingsByOwnerId(Long userId) {
    	System.out.println("service booking");
        return bookingRepository.findAllByOwnerId(userId);
    }

    public boolean isRoomAvailable(Long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        List<Booking> bookings = bookingRepository.findByRooms_IdAndCheckInDateBeforeAndCheckOutDateAfter(roomId, checkOutDate, checkInDate);
        return bookings.isEmpty();
    }

    private void updateRoomAvailability(Room room, LocalDate checkInDate, LocalDate checkOutDate, boolean isBooked) {
        adjustRoomAvailability(room, checkInDate, checkOutDate, isBooked);
    }

    private void restoreRoomAvailability(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        adjustRoomAvailability(room, checkInDate, checkOutDate, false);
    }

    private void adjustRoomAvailability(Room room, LocalDate checkInDate, LocalDate checkOutDate, boolean isBooked) {
        if (isBooked) {
            if (checkInDate.isBefore(room.getAvailableFrom())) {
                room.setAvailableFrom(checkInDate);
            }
            if (checkOutDate.isAfter(room.getAvailableTo())) {
                room.setAvailableTo(checkOutDate);
            }
        } else {
            room.setAvailableFrom(room.getAvailableFrom().minusDays(1));
            room.setAvailableTo(room.getAvailableTo().plusDays(1));
        }
        roomRepository.save(room);
    }

    private double calculateTotalAmount(Room room, LocalDate checkInDate, LocalDate checkOutDate, List<Integer> guestAges) {
        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        double baseFare = room.getBaseFare();
        double totalBaseFare = baseFare * nights;

        double additionalCharges = 0.0;
        int maxOccupancy = room.getMaxOccupancy();

        if (guestAges.size() > maxOccupancy) {
            throw new IllegalArgumentException("The number of guests exceeds the room's maximum occupancy.");
        }

        int startChargingIndex = getStartChargingIndex(room); // Get start charging index

        if (guestAges.size() > startChargingIndex) {
            List<Integer> extraGuestsAges = guestAges.subList(startChargingIndex, guestAges.size());
            for (Integer age : extraGuestsAges) {
                additionalCharges += calculateAdditionalCharge(baseFare, age);
            }
        }

        return totalBaseFare + additionalCharges;
    }

    private int getStartChargingIndex(Room room) {
        int maxOccupancy = room.getMaxOccupancy();

        switch (maxOccupancy) {
            case 2:
                return 1; 
            case 4:
                return 2; 
            case 6:
                return 4; 
            default:
                throw new IllegalArgumentException("Invalid maximum occupancy: " + maxOccupancy);
        }
    }

    private double calculateAdditionalCharge(double baseFare, Integer age) {
        double additionalChargeRate = (age > 14) ? 0.40 : 0.20;
        return baseFare * additionalChargeRate;
    }

    private List<List<Integer>> distributeGuests(List<Long> roomIds, List<Integer> guestAges) {
        List<List<Integer>> distributedGuests = new ArrayList<>();
        int index = 0;

        for (Long roomId : roomIds) {
            Room room = roomRepository.findById(roomId)
                    .orElseThrow(() -> new RoomNotFoundException("Room not found with ID: " + roomId));
            int maxOccupancy = room.getMaxOccupancy();
            List<Integer> roomGuests = new ArrayList<>();

            while (index < guestAges.size() && roomGuests.size() < maxOccupancy) {
                roomGuests.add(guestAges.get(index));
                index++;
            }

            distributedGuests.add(roomGuests);

            if (index >= guestAges.size()) {
                break;
            }
        }

        if (index < guestAges.size()) {
            throw new IllegalArgumentException("The number of guests exceeds the total occupancy of all rooms.");
        }

        return distributedGuests;
    }

    private void validateBookingCreateDTO(BookingCreateDTO bookingDTO) {
        if (bookingDTO.getUserId() == null) {
            throw new IllegalArgumentException("User ID must be provided for the booking.");
        }

        if (bookingDTO.getRoomIds() == null || bookingDTO.getRoomIds().isEmpty()) {
            throw new IllegalArgumentException("At least one room ID must be provided for the booking.");
        }

        if (bookingDTO.getCheckInDate() == null || bookingDTO.getCheckOutDate() == null) {
            throw new IllegalArgumentException("Check-in and check-out dates must be provided.");
        }

        if (bookingDTO.getCheckInDate().isAfter(bookingDTO.getCheckOutDate())) {
            throw new IllegalArgumentException("Check-in date must be before check-out date.");
        }
    }

    private void validateBookingUpdateDTO(BookingUpdateDTO bookingDTO) {
        if (bookingDTO.getCheckInDate() == null || bookingDTO.getCheckOutDate() == null) {
            throw new IllegalArgumentException("Check-in and check-out dates must be provided.");
        }

        if (bookingDTO.getCheckInDate().isAfter(bookingDTO.getCheckOutDate())) {
            throw new IllegalArgumentException("Check-in date must be before check-out date.");
        }
    }
}