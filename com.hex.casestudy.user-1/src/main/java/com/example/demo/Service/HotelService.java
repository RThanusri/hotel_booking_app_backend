package com.example.demo.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.Hotel;
import com.example.demo.Entities.HotelOwner;
import com.example.demo.Entities.User;
import com.example.demo.Exception.HotelNotFoundException;
import com.example.demo.dao.HotelRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.Dto.HotelDTO;
import com.example.demo.Mapper.HotelMapper;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelMapper hotelMapper;
    
    @Autowired
    private UserRepository userRepository;

    public HotelDTO addHotel(HotelDTO hotelDTO) {
        Hotel hotel = hotelMapper.toEntity(hotelDTO);
        System.out.println("sent hotel " + hotel.getId());

        // Fetch User by userId
        User user = userRepository.findById(hotelDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID " + hotelDTO.getUserId()));

        hotel.setUser(user);  // Set the user in the Hotel entity

        // Set the image URL
        hotel.setImage(hotelDTO.getImage());

        try {
            Hotel savedHotel = hotelRepository.save(hotel);
            System.out.println("saved hotel " + savedHotel.getName());
            return hotelMapper.toDto(savedHotel);
        } catch (Exception e) {
            System.out.println("Error while saving hotel: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public String removeHotel(int id) {
        if (hotelRepository.existsById(id)) {
            hotelRepository.deleteById(id);
            return "Hotel removed";
        } else {
            throw new HotelNotFoundException("Hotel with ID " + id + " not found");
        }
    }

    public List<HotelDTO> getAllHotels() {
        return hotelRepository.findAll().stream()
                .map(hotelMapper::toDto)
                .collect(Collectors.toList());
    }

    public Hotel getHotelById(int id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException("Hotel with ID " + id + " not found"));
        return hotel;
    }

    public HotelDTO getSpecificHotelByOwnerId(Long userId, int hotelId) {
    	Hotel hotel = hotelRepository.findSpecificHotelByOwnerId(userId, hotelId);
        if (hotel == null) {
            throw new HotelNotFoundException();
        }
        return hotelMapper.toDto(hotel);
    }

    public String updateHotel(int hotelId, HotelDTO hotelDTO) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException("Hotel with ID " + hotelId + " not found"));

        if (hotelDTO.getName() != null) {
            hotel.setName(hotelDTO.getName());
        }
        if (hotelDTO.getAddress() != null) {
            hotel.setAddress(hotelDTO.getAddress());
        }
        if (hotelDTO.getPhoneNo() != null) {
            hotel.setPhoneNo(hotelDTO.getPhoneNo());
        }
        if (hotelDTO.getDescription() != null) {
            hotel.setDescription(hotelDTO.getDescription());
        }
        if (hotelDTO.getAmenities() != null) {
            hotel.setAmenities(hotelDTO.getAmenities());
        }
        if (hotelDTO.getNoOfRooms() > 0) {
            hotel.setNoOfRooms(hotelDTO.getNoOfRooms());
        }
        if (hotelDTO.getImage() != null) {
            hotel.setImage(hotelDTO.getImage());
        }

     
        if (hotelDTO.getUserId() != 0) {
            User user = new User();
            user.setUserId(hotelDTO.getUserId()); 
            hotel.setUser(user); 
        }

        hotelRepository.save(hotel);
        return "Hotel details updated";
    }

    public List<HotelDTO> getAllHotelsByOwnerId(Long userId) {
    	return hotelRepository.findHotelsByOwnerId(userId).stream()
                .map(hotelMapper::toDto)
                .collect(Collectors.toList());
    }
    public List<HotelDTO> getHotelsByAmenities(String amenities) {
        if (amenities == null || amenities.isEmpty()) {
            return List.of(); 
        }
        
        return hotelRepository.findByAmenitiesContainingIgnoreCase(amenities).stream().map(hotelMapper::toDto).collect(Collectors.toList());
    }
   

	public List<HotelDTO> getHotelsByAddress(String address) {
		
		  if (address == null || address.isEmpty()) {
	            return List.of(); 
	        }
	        
	        return hotelRepository.findByAddressContainingIgnoreCase(address).stream().map(hotelMapper::toDto).collect(Collectors.toList());
	    }
	}
    

