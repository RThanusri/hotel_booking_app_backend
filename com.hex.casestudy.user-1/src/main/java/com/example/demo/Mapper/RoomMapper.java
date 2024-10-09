package com.example.demo.Mapper;

import org.springframework.stereotype.Component;
import com.example.demo.Dto.RoomDTO;
import com.example.demo.Entities.Room;
import com.example.demo.Service.HotelService;

@Component
public class RoomMapper {

    private final HotelService hotelService;

    public RoomMapper(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    public RoomDTO toDTO(Room room) {
        if (room == null) {
            return null;
        }
        RoomDTO dto = new RoomDTO();
        dto.setId(room.getId());
        dto.setHotelId(room.getHotel().getId());
        dto.setRoomSize(room.getRoomSize());
        dto.setBedSize(room.getBedSize());
        dto.setMaxOccupancy(room.getMaxOccupancy());
        dto.setBaseFare(room.getBaseFare());
        dto.setAC(room.isAC());
        dto.setAvailableFrom(room.getAvailableFrom());
        dto.setAvailableTo(room.getAvailableTo());
        
     
        if (room.getImages() != null) {
            dto.setImages(room.getImages());
        }

        return dto;
    }

    public Room toEntity(RoomDTO dto) {
        if (dto == null) {
            return null;
        }
        Room room = new Room();
        room.setId(dto.getId());
        room.setRoomSize(dto.getRoomSize());
        room.setBedSize(dto.getBedSize());
        room.setMaxOccupancy(dto.getMaxOccupancy());
        room.setBaseFare(dto.getBaseFare());
        room.setAC(dto.isAC());
        room.setAvailableFrom(dto.getAvailableFrom());
        room.setAvailableTo(dto.getAvailableTo());

    
        if (dto.getImages() != null) {
            room.setImages(dto.getImages());
        }

        if (dto.getHotelId() != 0) {
            room.setHotel(hotelService.getHotelById(dto.getHotelId()));
        }

        return room;
    }
}
