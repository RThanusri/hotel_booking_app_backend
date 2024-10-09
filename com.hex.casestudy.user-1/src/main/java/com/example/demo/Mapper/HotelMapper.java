package com.example.demo.Mapper;

import com.example.demo.Dto.HotelDTO;
import com.example.demo.Entities.Hotel;
import com.example.demo.Entities.User;  // Import User entity
import org.springframework.stereotype.Component;

@Component
public class HotelMapper {

    // Convert Hotel Entity to HotelDTO
    public HotelDTO toDto(Hotel hotel) {
        if (hotel == null) {
            return null;
        }
        HotelDTO dto = new HotelDTO();
        dto.setId(hotel.getId());
        dto.setName(hotel.getName());
        dto.setAddress(hotel.getAddress());
        dto.setPhoneNo(hotel.getPhoneNo());
        dto.setDescription(hotel.getDescription());
        dto.setAmenities(hotel.getAmenities());
        dto.setNoOfRooms(hotel.getNoOfRooms());
        dto.setImage(hotel.getImage());

        // Set userId from the associated User entity
        if (hotel.getUser() != null) {
            dto.setUserId(hotel.getUser().getUserId());
        }

        return dto;
    }

    // Convert HotelDTO to Hotel Entity
    public Hotel toEntity(HotelDTO dto) {
        if (dto == null) {
            return null;
        }
        Hotel hotel = new Hotel();
        hotel.setId(dto.getId());
        hotel.setName(dto.getName());
        hotel.setAddress(dto.getAddress());
        hotel.setPhoneNo(dto.getPhoneNo());
        hotel.setDescription(dto.getDescription());
        hotel.setAmenities(dto.getAmenities());
        hotel.setNoOfRooms(dto.getNoOfRooms());
        hotel.setImage(dto.getImage());

        // Set the User entity based on userId
        if (dto.getUserId() != 0) {
            User user = new User();
            user.setUserId(dto.getUserId()); 
            hotel.setUser(user);  // Set the User entity as the owner
        }

        return hotel;
    }
}
