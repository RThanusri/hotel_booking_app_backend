package com.example.demo.Mapper;

import org.springframework.stereotype.Component;
import com.example.demo.Dto.BookingDTO;
import com.example.demo.Entities.Booking;
import com.example.demo.Entities.Room;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingMapper {

    public BookingDTO toDTO(Booking booking) {
        if (booking == null) {
            return null;
        }
        BookingDTO dto = new BookingDTO();
        dto.setBookingId(booking.getBookingId());
        dto.setCheckInDate(booking.getCheckInDate());
        dto.setCheckOutDate(booking.getCheckOutDate());
        dto.setNumberOfAdults(booking.getNumberOfAdults());
        dto.setNumberOfChildren(booking.getNumberOfChildren());
        dto.setNumberOfRooms(booking.getNumberOfRooms());
        dto.setRoomIds(booking.getRooms().stream()
                            .map(Room::getId)
                            .collect(Collectors.toList()));
        return dto;
    }

    public Booking toEntity(BookingDTO dto, List<Room> rooms) {
        if (dto == null) {
            return null;
        }
        Booking booking = new Booking();
        booking.setBookingId(dto.getBookingId());
        booking.setCheckInDate(dto.getCheckInDate());
        booking.setCheckOutDate(dto.getCheckOutDate());
        booking.setNumberOfAdults(dto.getNumberOfAdults());
        booking.setNumberOfChildren(dto.getNumberOfChildren());
        booking.setNumberOfRooms(dto.getNumberOfRooms());
        booking.setRooms(rooms);
        return booking;
    }
}
