package com.example.demo.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Dto.HotelDTO;
import com.example.demo.Dto.RoomDTO;
import com.example.demo.Entities.Hotel;
import com.example.demo.Entities.HotelOwner;
import com.example.demo.Service.HotelOwnerService;
import com.example.demo.Service.HotelService;
import com.example.demo.Service.RoomService;

@RestController
@CrossOrigin("http://localhost:3000")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private HotelOwnerService hotelOwnerService;

    @Autowired
    private RoomService roomService;

    @PostMapping("/api/owner/addHotel")
    public ResponseEntity<HotelDTO> saveHotel(@RequestBody HotelDTO hotelDTO) {
        HotelDTO savedHotel = hotelService.addHotel(hotelDTO);
        return new ResponseEntity<>(savedHotel, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/owner/removeHotel/{hotelID}")
    public ResponseEntity<String> deleteHotel(@PathVariable int hotelID) {
        String result = hotelService.removeHotel(hotelID);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/api/user/searchHotel")
    public ResponseEntity<List<HotelDTO>> searchHotels(
            @RequestParam String address,
            @RequestParam LocalDate checkInDate,
            @RequestParam LocalDate checkOutDate,
            @RequestParam int numberOfRooms,
            @RequestParam int numberOfAdults,
            @RequestParam int numberOfChildren){
            
        List<HotelDTO> hotels = roomService.searchHotels(address, checkInDate, checkOutDate,numberOfRooms,numberOfChildren,numberOfAdults);
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    @PutMapping("/api/owner/updateHotel/{hotelId}")
    public ResponseEntity<String> updateHotel(@PathVariable int hotelId, @RequestBody HotelDTO hotelDTO) {
        String result = hotelService.updateHotel(hotelId, hotelDTO);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/api/owner/updateHotelOwner/{hotelOwnerId}")
    public ResponseEntity<String> updateHotelOwner(@PathVariable int hotelOwnerId, @RequestBody HotelOwner hotelOwner) {
        String result = hotelOwnerService.updateHotelOwner(hotelOwnerId, hotelOwner);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/user/getAllHotels")
    public ResponseEntity<List<HotelDTO>> getHotels() {
        List<HotelDTO> hotels = hotelService.getAllHotels();
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    @GetMapping("/api/shared/getHotelById/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable int id) {
        Hotel hotel = hotelService.getHotelById(id);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

   
    @DeleteMapping("/api/owner/removeHotelOwner/{ownerID}")
    public ResponseEntity<String> deleteHotelOwner(@PathVariable int ownerID) {
        String result = hotelOwnerService.removeHotelOwner(ownerID);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    

    @GetMapping("/api/admin/getAllHotelOwners")
    public ResponseEntity<List<HotelOwner>> getHotelOwners() {
        List<HotelOwner> hotelOwners = hotelOwnerService.getAllHotelOwners();
        return new ResponseEntity<>(hotelOwners, HttpStatus.OK);
    }

    @GetMapping("/api/admin/getHotelOwnerById/{id}")
    public ResponseEntity<HotelOwner> getHotelOwnerById(@PathVariable int id) {
        HotelOwner hotelOwner = hotelOwnerService.getHotelOwnerById(id);
        return new ResponseEntity<>(hotelOwner, HttpStatus.OK);
    }

    @PostMapping("/api/owner/addRoom")
    public ResponseEntity<RoomDTO> saveRoom(@RequestBody RoomDTO roomDTO) {
    	try {
        RoomDTO savedRoom = roomService.addRoom(roomDTO);
        System.out.println(savedRoom);
        return ResponseEntity.ok(savedRoom);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return new ResponseEntity<>(null);
    	}
    }

    @DeleteMapping("/api/owner/removeRoom/{roomID}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long roomID) {
        roomService.deleteRoom(roomID);
        return new ResponseEntity<>("Room deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/api/user/searchRooms")
    public ResponseEntity<List<RoomDTO>> searchRooms(
    		@RequestParam int hotelId,
            @RequestParam String address,
            @RequestParam LocalDate checkInDate,
            @RequestParam LocalDate checkOutDate,
            @RequestParam int numberOfRooms,
            @RequestParam int numberOfAdults,
            @RequestParam int numberOfChildren) {

        List<RoomDTO> rooms = roomService.searchRooms(hotelId, address, checkInDate, checkOutDate, numberOfRooms, numberOfAdults, numberOfChildren);
        return ResponseEntity.ok(rooms);
    }
    @PutMapping("/api/owner/updateRoom/{roomId}")
    public ResponseEntity<String> updateRoom(@PathVariable Long roomId, @RequestBody RoomDTO roomDTO) {
        roomService.updateRoom(roomId, roomDTO);
        return ResponseEntity.ok("Room updated successfully");
    }

    @GetMapping("/api/shared/allRoomsInHotel/{id}")
    public ResponseEntity<List<RoomDTO>> getAllRooms(@PathVariable int id) {
        List<RoomDTO> rooms = roomService.getAllRoomsByHotelId(id);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/api/owner/getRoomById/{roomId}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long roomId) {
        RoomDTO room = roomService.getRoomById(roomId);
        return ResponseEntity.ok(room);
    }
   
    @GetMapping("/api/user/searchHotelByAmenities")
    public ResponseEntity<List<HotelDTO>> getHotelsByAmenities(
            @RequestParam String amenities){
        
        List<HotelDTO> hotels = hotelService.getHotelsByAmenities(amenities);
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }
    @GetMapping("/api/user/searchHotelByAddress")
    public ResponseEntity<List<HotelDTO>> getHotelsByAddress(
            @RequestParam String address){
        
        List<HotelDTO> hotels = hotelService.getHotelsByAddress(address);
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }
    @GetMapping("/api/owner/getAllHotelsByOwner/{userId}")
    public ResponseEntity<List<HotelDTO>> getHotelsByOwnerId(@PathVariable Long userId) {
    	try {
        List<HotelDTO> hotels = hotelService.getAllHotelsByOwnerId(userId);
        System.out.println(hotels);
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return new ResponseEntity<>(null);
    	}
    }

    	
    
}