package com.example.demo.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Dto.HotelDTO;
import com.example.demo.Dto.RoomDTO;
import com.example.demo.Entities.Hotel;
import com.example.demo.Entities.Room;
import com.example.demo.Exception.RoomNotFoundException;
import com.example.demo.Mapper.RoomMapper;
import com.example.demo.Mapper.HotelMapper;
import com.example.demo.dao.HotelRepository;
import com.example.demo.dao.RoomRepository;

@Service
@Transactional
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private HotelMapper hotelMapper;

    public RoomDTO addRoom(RoomDTO roomDTO) {
        Room room = roomMapper.toEntity(roomDTO);
        if (roomDTO.getHotelId() != 0) {
            room.setHotel(hotelService.getHotelById(roomDTO.getHotelId()));
        }

        room.setId(roomDTO.getId());
        
        // Set images
        if (roomDTO.getImages() != null) {
            room.setImages(roomDTO.getImages());
        }

        Room savedRoom = roomRepository.save(room);
        return roomMapper.toDTO(savedRoom);
    }

    public RoomDTO updateRoom(Long roomId, RoomDTO roomDTO) {
        // Retrieve existing room by ID or throw exception if not found
        Room existingRoom = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room not found with ID: " + roomId));

        // Update room size if it's not null
        if (roomDTO.getRoomSize() != null) {
            existingRoom.setRoomSize(roomDTO.getRoomSize());
        }

        // Update bed size if it's not null
        if (roomDTO.getBedSize() != null) {
            existingRoom.setBedSize(roomDTO.getBedSize());
        }

        
        if (roomDTO.getMaxOccupancy() > 0) {
            existingRoom.setMaxOccupancy(roomDTO.getMaxOccupancy());
        }

    
        if (roomDTO.getBaseFare() > 0) {
            existingRoom.setBaseFare(roomDTO.getBaseFare());
        }

      
        if (roomDTO.isAC() != false) {
            existingRoom.setAC(roomDTO.isAC());
        }

      
        if (roomDTO.getAvailableFrom() != null) {
            existingRoom.setAvailableFrom(roomDTO.getAvailableFrom());
        }

        
        if (roomDTO.getAvailableTo() != null) {
            existingRoom.setAvailableTo(roomDTO.getAvailableTo());
        }

     
        if (roomDTO.getImages() != null) {
            existingRoom.setImages(roomDTO.getImages());
        }

     
        if (roomDTO.getHotelId() != 0) {
            Hotel hotel = hotelRepository.findById(roomDTO.getHotelId())
                    .orElseThrow(() -> new IllegalArgumentException("Hotel not found with ID: " + roomDTO.getHotelId()));
            existingRoom.setHotel(hotel);
        }

    
        Room updatedRoom = roomRepository.save(existingRoom);
        return roomMapper.toDTO(updatedRoom);
    }
    public RoomDTO getRoomById(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room not found with ID: " + roomId));
        return roomMapper.toDTO(room);
    }

    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(roomMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<RoomDTO> getAllRoomsByHotelId(int hotelId) {
        return roomRepository.findByHotelId(hotelId).stream()
                .map(roomMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room not found with ID: " + roomId));
        roomRepository.delete(room);
    }

    public List<RoomDTO> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, 
            int numberOfAdults, int numberOfChildren, int hotelId) {
        validateDates(checkInDate, checkOutDate);
        validateOccupancy(numberOfAdults, numberOfChildren);

        int totalOccupancy = numberOfAdults + numberOfChildren;
        return roomRepository.findAvailableRooms(checkInDate, checkOutDate, hotelId).stream()
                .filter(room -> room.getMaxOccupancy() >= totalOccupancy) 
                .map(roomMapper::toDTO)
                .collect(Collectors.toList());
    }
    public List<HotelDTO> searchHotels(String address, LocalDate checkInDate, LocalDate checkOutDate, int numberOfRooms, int numberOfAdults, int numberOfChildren) {
        validateDates(checkInDate, checkOutDate);

        List<Room> availableRooms = roomRepository.findByAvailableFromLessThanEqualAndAvailableToGreaterThanEqual(checkInDate, checkOutDate);
        
        List<Room> filteredRooms = availableRooms.stream()
                .filter(room -> room.getMaxOccupancy() >= (numberOfAdults + numberOfChildren))
                .collect(Collectors.toList());

        List<Integer> hotelIds = filteredRooms.stream()
                .map(room -> room.getHotel().getId())
                .distinct()
                .collect(Collectors.toList());

     
        String[] searchTerms = address.toLowerCase().trim().split("\\s*,\\s*"); 

     
        List<Hotel> hotels = hotelRepository.findAll(); 
        List<Hotel> matchedHotels = hotels.stream()
                .filter(hotel -> hotelIds.contains(hotel.getId())) 
                .filter(hotel -> containsAnyWord(hotel.getAddress(), searchTerms)) 
                .collect(Collectors.toList());

        return matchedHotels.stream()
                .map(hotelMapper::toDto)
                .collect(Collectors.toList());
    }

  
    private boolean containsAnyWord(String hotelAddress, String[] searchTerms) {
        String normalizedAddress = hotelAddress.toLowerCase();
        for (String term : searchTerms) {
            if (normalizedAddress.contains(term.trim())) {
                return true;
            }
        }
        return false; 
    }



    public List<RoomDTO> searchRooms(int hotelId, String address, LocalDate checkInDate, LocalDate checkOutDate,
            int numberOfAdults, int numberOfChildren , int numberOfRooms) {
validateDates(checkInDate, checkOutDate);
validateOccupancy(numberOfAdults, numberOfChildren);

int minOccupancy = numberOfAdults + numberOfChildren;

List<Room> availableRooms = roomRepository.findByAvailableFromLessThanEqualAndAvailableToGreaterThanEqual(checkInDate, checkOutDate);

List<Room> filteredRooms = availableRooms.stream()
.filter(room -> room.getMaxOccupancy() >= minOccupancy)
.filter(room -> room.getHotel().getId() == hotelId) 
.collect(Collectors.toList());


String[] searchTerms = address.toLowerCase().trim().split("\\s*,\\s*");
filteredRooms = filteredRooms.stream()
.filter(room -> containsAnyWord(room.getHotel().getAddress(), searchTerms))
.collect(Collectors.toList());

return filteredRooms.stream()
.map(roomMapper::toDTO)
.collect(Collectors.toList());
}




    private void validateDates(LocalDate checkInDate, LocalDate checkOutDate) {
        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Check-in and check-out dates must be provided.");
        }
        if (checkInDate.isAfter(checkOutDate)) {
            throw new IllegalArgumentException("Check-out date must be after check-in date.");
        }
    }

    private void validateOccupancy(int numberOfAdults, int numberOfChildren) {
        if (numberOfAdults < 0 && numberOfChildren < 0) {
            throw new IllegalArgumentException("Number of adults and children cannot be negative.");
        }
    }
    
}
