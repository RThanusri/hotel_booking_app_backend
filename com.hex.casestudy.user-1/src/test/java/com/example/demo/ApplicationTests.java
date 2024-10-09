package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.Dto.BookingCreateDTO;
import com.example.demo.Dto.BookingDTO;
import com.example.demo.Dto.BookingResponseDTO;
import com.example.demo.Dto.HotelDTO;
import com.example.demo.Dto.ReviewDTO;
import com.example.demo.Dto.RoomDTO;
import com.example.demo.Entities.AuthenticationRequest;
import com.example.demo.Entities.AuthenticationResponse;
import com.example.demo.Entities.Booking;
import com.example.demo.Entities.BookingStatus;
import com.example.demo.Entities.Hotel;
import com.example.demo.Entities.HotelOwner;
import com.example.demo.Entities.Review;
import com.example.demo.Entities.Room;
import com.example.demo.Entities.SignupRequest;
import com.example.demo.Entities.User;
import com.example.demo.Enum.Role;
import com.example.demo.Mapper.BookingMapper;
import com.example.demo.Mapper.RoomMapper;
import com.example.demo.Service.AuthService;
import com.example.demo.Service.BookingService;
import com.example.demo.Service.HotelOwnerService;
import com.example.demo.Service.HotelService;
import com.example.demo.Service.ReviewService;
import com.example.demo.Service.RoomService;
import com.example.demo.Service.UserService;
import com.example.demo.Service.UserServices;
import com.example.demo.dao.BookingRepository;
import com.example.demo.dao.HotelOwnerRepository;
import com.example.demo.dao.HotelRepository;
import com.example.demo.dao.RoomRepository;
import com.example.demo.dao.UserRepository;

@SpringBootTest
class ApplicationTests {
	
	@Autowired
	UserServices userService;
	@Autowired
	HotelService hotelService;
	
	@Autowired
	ReviewService reviewService;
	@Autowired
	RoomService roomService;
	
	 @Autowired
	 HotelOwnerService hotelOwnerService;
	
	 @Autowired
	 BookingService  bookingService;
	
	 @Autowired
	 AuthService authService ;
	 


	@Test
	void registerUser() {
		
		SignupRequest signupRequest = new SignupRequest("john.doe@example.com", "John_Doe", "password123", Role.USER);
        User createdUser=authService.createUser(signupRequest);
        //userService.deleteUser(createdUser.getUserId());
	}
        

@Test
void forgetPassword()
{
	SignupRequest signupRequest = new SignupRequest("johnny@example.com", "John_Doe", "Password123", Role.USER);
	User createdUser=authService.createUser(signupRequest);
    String newPassword ="Swetha33";
    userService.updatePassword(createdUser.getEmail(),newPassword);
    //userService.deleteUser(createdUser.getUserId());
}

@Test
void getUserDetails()
{	SignupRequest signupRequest = new SignupRequest("john.doe@example.com", "John_Doe", "password123", Role.USER);
	User createdUser=authService.createUser(signupRequest);
	userService.getUserId(createdUser.getUserId());
	userService.deleteUser(createdUser.getUserId());
}
@Test

void getAllUsersDetails()
{
	userService.getAllUser();
}
@Test
void deleteUser()
{
	SignupRequest signupRequest = new SignupRequest("john.doe@example.com", "John_Doe", "password123", Role.USER);
	User createdUser = authService.createUser(signupRequest);
	
	userService.deleteUser(createdUser.getUserId());
}

@Test
void login() {
	SignupRequest signupRequest = new SignupRequest("john.doe@example.com", "John_Doe", "password123", Role.USER);
	User createdUser = authService.createUser(signupRequest);
	
	
    AuthenticationRequest request = new AuthenticationRequest(createdUser.getEmail(),  "password123");
    
    AuthenticationResponse response = authService.login(request);
    
    assertNotNull(response);
    assertNotNull(response.getJwt());
    
    userService.deleteUser(createdUser.getUserId());
    
}

@Test
void addReview() {

	SignupRequest signupRequest = new SignupRequest("johnnydain@example.com", "John_Doe", "Password123", Role.HOTEL_OWNER);
	User createdUser=authService.createUser(signupRequest);
    

    
    HotelDTO hotel = new HotelDTO();
    hotel.setName("Test Hotel");
    hotel.setAddress("Test Address");
    hotel.setPhoneNo("1234567890");
    hotel.setDescription("Test Description");
    hotel.setAmenities("WiFi, Breakfast");
    hotel.setNoOfRooms(10);
    hotel.setImage("test.jpg");
    hotel.setUserId(createdUser.getUserId());

    HotelDTO createdHotel = hotelService.addHotel(hotel);

  
    SignupRequest signUpRequest = new SignupRequest("john.doe@example.com", "John_Doe", "Password123", Role.USER);
    User user = authService.createUser(signUpRequest);

   
    ReviewDTO review = new ReviewDTO();
    review.setReviewText("Great stay with excellent service.");
    review.setHotelId(createdHotel.getId());
    review.setUserId(user.getUserId());
    
   
    review.setCleanlinessRating(4);
    review.setStaffServiceRating(5);
    review.setAmenitiesRating(4);
    review.setPropertyConditionsRating(5);
    review.setEcoFriendlinessRating(4);

  
    ReviewDTO createdReview = reviewService.saveReview(review);
    

    reviewService.deleteReview(createdReview.getReviewId());
    userService.deleteUser(user.getUserId());
    hotelService.removeHotel(createdHotel.getId());
    
    userService.deleteUser(createdUser.getUserId());
    
}
@Test
void getHotelReviews() {
    // Set up hotel owner and hotel
    SignupRequest ownerRequest = new SignupRequest("hotelowner@example.com", "HotelOwner", "OwnerPassword", Role.HOTEL_OWNER);
    User createdHotelOwner = authService.createUser(ownerRequest);
    
    HotelDTO hotel = new HotelDTO();
    hotel.setName("Test Hotel");
    hotel.setAddress("Test Address");
    hotel.setPhoneNo("1234567890");
    hotel.setDescription("Test Description");
    hotel.setAmenities("WiFi, Breakfast");
    hotel.setNoOfRooms(10);
    hotel.setImage("test.jpg");
    hotel.setUserId(createdHotelOwner.getUserId());

    HotelDTO createdHotel = hotelService.addHotel(hotel);

    // Create a user and a review
    SignupRequest userRequest = new SignupRequest("user@example.com", "User", "UserPassword", Role.USER);
    User user = authService.createUser(userRequest);

    ReviewDTO review = new ReviewDTO();
    review.setReviewText("Great stay with excellent service.");
    review.setHotelId(createdHotel.getId());
    review.setUserId(user.getUserId());
    review.setCleanlinessRating(4);
    review.setStaffServiceRating(5);
    review.setAmenitiesRating(4);
    review.setPropertyConditionsRating(5);
    review.setEcoFriendlinessRating(4);

    ReviewDTO createdReview = reviewService.saveReview(review);

    
     reviewService.getHotelReviews(createdHotel.getId());

  
    reviewService.deleteReview(createdReview.getReviewId());
    userService.deleteUser(user.getUserId());
    hotelService.removeHotel(createdHotel.getId());
    userService.deleteUser(createdHotelOwner.getUserId());
    
}

@Test
void getAllUserGivenReviews() {
    // Set up user and hotel
    SignupRequest userRequest = new SignupRequest("user2@example.com", "User2", "UserPassword", Role.USER);
    User user = authService.createUser(userRequest);

    SignupRequest ownerRequest = new SignupRequest("hotelowner2@example.com", "HotelOwner2", "OwnerPassword", Role.HOTEL_OWNER);
    User createdHotelOwner = authService.createUser(ownerRequest);

    HotelDTO hotel = new HotelDTO();
    hotel.setName("Test Hotel 2");
    hotel.setAddress("Test Address 2");
    hotel.setPhoneNo("0987654321");
    hotel.setDescription("Test Description 2");
    hotel.setAmenities("Pool, Gym");
    hotel.setNoOfRooms(20);
    hotel.setImage("test2.jpg");
    hotel.setUserId(createdHotelOwner.getUserId());

    HotelDTO createdHotel = hotelService.addHotel(hotel);

    ReviewDTO review = new ReviewDTO();
    review.setReviewText("Excellent stay!");
    review.setHotelId(createdHotel.getId());
    review.setUserId(user.getUserId());
    review.setCleanlinessRating(5);
    review.setStaffServiceRating(5);
    review.setAmenitiesRating(4);
    review.setPropertyConditionsRating(5);
    review.setEcoFriendlinessRating(5);

    ReviewDTO createdReview = reviewService.saveReview(review);

 
   reviewService.getAllUserGivenReviews(user.getUserId());
   


    reviewService.deleteReview(createdReview.getReviewId());
    userService.deleteUser(user.getUserId());
    hotelService.removeHotel(createdHotel.getId());
    userService.deleteUser(createdHotelOwner.getUserId());
}

@Test
void deleteReview() {
  
    SignupRequest ownerRequest = new SignupRequest("deleteowner@example.com", "DeleteOwner", "OwnerPassword", Role.HOTEL_OWNER);
    User createdHotelOwner = authService.createUser(ownerRequest);
    
    HotelDTO hotel = new HotelDTO();
    hotel.setName("Delete Test Hotel");
    hotel.setAddress("Delete Test Address");
    hotel.setPhoneNo("1231231234");
    hotel.setDescription("Delete Test Description");
    hotel.setAmenities("WiFi, Gym");
    hotel.setNoOfRooms(5);
    hotel.setImage("deleteTest.jpg");
    hotel.setUserId(createdHotelOwner.getUserId());

    HotelDTO createdHotel = hotelService.addHotel(hotel);

   
    SignupRequest userRequest = new SignupRequest("deletereviewuser@example.com", "DeleteUser", "UserPassword", Role.USER);
    User user = authService.createUser(userRequest);

    ReviewDTO review = new ReviewDTO();
    review.setReviewText("This review will be deleted.");
    review.setHotelId(createdHotel.getId());
    review.setUserId(user.getUserId());
    review.setCleanlinessRating(3);
    review.setStaffServiceRating(3);
    review.setAmenitiesRating(3);
    review.setPropertyConditionsRating(3);
    review.setEcoFriendlinessRating(3);

    ReviewDTO createdReview = reviewService.saveReview(review);

   
    reviewService.deleteReview(createdReview.getReviewId());


   reviewService.getAllUserGivenReviews(user.getUserId());
   


    userService.deleteUser(user.getUserId());
    hotelService.removeHotel(createdHotel.getId());
    userService.deleteUser(createdHotelOwner.getUserId());}

@Test
void addHotelOwner() {
	HotelOwner owner = new HotelOwner();
	owner.setName("ram");
	owner.setAddress("thiruvananthapuram");
	owner.setPhoneNo("9012345678");
	
	HotelOwner createdHotelOwner=hotelOwnerService.addHotelOwner(owner);
	hotelOwnerService.removeHotelOwner(createdHotelOwner.getOwnerId());
}
@Test
void addHotel() {
		
	SignupRequest userRequest = new SignupRequest("user2@example.com", "User2", "UserPassword", Role.ADMIN);
    User user = authService.createUser(userRequest);

    

        HotelDTO hotel = new HotelDTO();
        hotel.setName("Test Hotel");
        hotel.setAddress("Test Address");
        hotel.setPhoneNo("1234567890");
        hotel.setDescription("Test Description");
        hotel.setAmenities("WiFi, Breakfast");
        hotel.setNoOfRooms(10);
        hotel.setImage("test.jpg");
        hotel.setUserId(user.getUserId());

        HotelDTO createdHotel=hotelService.addHotel(hotel);
        hotelService.removeHotel(createdHotel.getId());
    	userService.deleteUser(user.getUserId());
}

@Test
void deleteHotel() {
	
	SignupRequest userRequest = new SignupRequest("user2@example.com", "User2", "UserPassword", Role.ADMIN);
    User user = authService.createUser(userRequest);
        HotelDTO hotel = new HotelDTO();
        hotel.setName("Test Hotel");
        hotel.setAddress("Test Address");
        hotel.setPhoneNo("1234567890");
        hotel.setDescription("Test Description");
        hotel.setAmenities("WiFi, Breakfast");
        hotel.setNoOfRooms(10);
        hotel.setImage("test.jpg");
        hotel.setUserId(user.getUserId());

        HotelDTO createdHotel=hotelService.addHotel(hotel);
	hotelService.removeHotel(createdHotel.getId());
	userService.deleteUser(user.getUserId());
}

@Test
void deleteHotelOwner() {
	HotelOwner owner = new HotelOwner();
	owner.setName("ram");
	owner.setAddress("thiruvananthapuram");
	owner.setPhoneNo("9012345678");
	
	HotelOwner createdHotelOwner=hotelOwnerService.addHotelOwner(owner);
	hotelOwnerService.removeHotelOwner(createdHotelOwner.getOwnerId());
}

@Test
void getHotelbyId() {
	SignupRequest userRequest = new SignupRequest("user2@example.com", "User2", "UserPassword", Role.USER);
    User user = authService.createUser(userRequest);

        HotelDTO hotel = new HotelDTO();
        hotel.setName("Test Hotel");
        hotel.setAddress("Test Address");
        hotel.setPhoneNo("1234567890");
        hotel.setDescription("Test Description");
        hotel.setAmenities("WiFi, Breakfast");
        hotel.setNoOfRooms(10);
        hotel.setImage("test.jpg");
        hotel.setUserId(user.getUserId());

        HotelDTO createdHotel=hotelService.addHotel(hotel);
        hotelService.getHotelById(createdHotel.getId());
        hotelService.removeHotel(createdHotel.getId());
        userService.deleteUser(user.getUserId());
}



@Test 
void getAllHotels() {
	hotelService.getAllHotels();
}

@Test 
void getAllHotelOwners() {
	hotelOwnerService.getAllHotelOwners();
}

@Test
void updateHotel() {
	SignupRequest userRequest = new SignupRequest("user2@example.com", "User2", "UserPassword", Role.ADMIN);
    User user = authService.createUser(userRequest);

        HotelDTO hotel = new HotelDTO();
        hotel.setName("Test Hotel");
        hotel.setAddress("Test Address");
        hotel.setPhoneNo("1234567890");
        hotel.setDescription("Test Description");
        hotel.setAmenities("WiFi, Breakfast");
        hotel.setNoOfRooms(10);
        hotel.setImage("test.jpg");
        hotel.setUserId(user.getUserId());

        HotelDTO createdHotel=hotelService.addHotel(hotel);
	
	createdHotel.setName("lakshmi hotel");
	
	hotelService.addHotel(createdHotel);
	
	assertEquals(createdHotel.getName(), "lakshmi hotel", "");
	
	hotelService.removeHotel(createdHotel.getId());
	userService.deleteUser(user.getUserId());
	
}

@Test
void updateHotelOwner() {
	
	HotelOwner owner = new HotelOwner();
	owner.setName("ram");
	owner.setAddress("thiruvananthapuram");
	owner.setPhoneNo("9012345678");
	
	HotelOwner createdHotelOwner=hotelOwnerService.addHotelOwner(owner);
	
	createdHotelOwner.setName("rahul vellal");
	
	hotelOwnerService.addHotelOwner(createdHotelOwner);
	
	assertEquals(createdHotelOwner.getName(), "rahul vellal", "");
	hotelOwnerService.removeHotelOwner(createdHotelOwner.getOwnerId());
}
@Test
void addRoom() {
	SignupRequest userRequest = new SignupRequest("user2@example.com", "User2", "UserPassword", Role.ADMIN);
    User user = authService.createUser(userRequest);

        HotelDTO hotel = new HotelDTO();
        hotel.setName("Test Hotel");
        hotel.setAddress("Test Address");
        hotel.setPhoneNo("1234567890");
        hotel.setDescription("Test Description");
        hotel.setAmenities("WiFi, Breakfast");
        hotel.setNoOfRooms(10);
        hotel.setImage("test.jpg");
        hotel.setUserId(user.getUserId());

        HotelDTO createdHotel=hotelService.addHotel(hotel);
        RoomDTO room = new RoomDTO();
        room.setHotelId(createdHotel.getId());
        room.setRoomSize("Large");
        room.setBedSize("king");
        room.setMaxOccupancy(6);
        room.setBaseFare(150.0);
        room.setAC(true);

        
        List<String> images = Arrays.asList("image1.jpg", "image2.jpg", "image3.jpg");
        room.setImages(images);
    RoomDTO createdRoom=roomService.addRoom(room);
    roomService.deleteRoom(createdRoom.getId());
    hotelService.removeHotel(createdHotel.getId());
    userService.deleteUser(user.getUserId());
}
@Test
void updateRoom() {
	
	SignupRequest userRequest = new SignupRequest("user2@example.com", "User2", "UserPassword", Role.ADMIN);
    User user = authService.createUser(userRequest);

        HotelDTO hotel = new HotelDTO();
        hotel.setName("Test Hotel");
        hotel.setAddress("Test Address");
        hotel.setPhoneNo("1234567890");
        hotel.setDescription("Test Description");
        hotel.setAmenities("WiFi, Breakfast");
        hotel.setNoOfRooms(10);
        hotel.setImage("test.jpg");
        hotel.setUserId(user.getUserId());

        HotelDTO createdHotel=hotelService.addHotel(hotel);
    RoomDTO room = new RoomDTO();
    room.setHotelId(createdHotel.getId());
    room.setRoomSize("Large");
    room.setBedSize("king");
    room.setMaxOccupancy(6);
    room.setBaseFare(150.0);
    room.setAC(true);
    room.setAvailableFrom(LocalDate.now());
    room.setAvailableTo(LocalDate.now().plusDays(30));

    RoomDTO createdRoom=roomService.addRoom(room);
    createdRoom.setAC(false);
    createdRoom.setAvailableFrom(LocalDate.now());
    createdRoom.setAvailableTo(LocalDate.now().plusDays(30));
    createdRoom.setBaseFare(200);
    createdRoom.setBedSize("king");
    createdRoom.setHotelId(createdHotel.getId());
    
    createdRoom.setMaxOccupancy(6);
    createdRoom.setRoomSize("Large");
    RoomDTO updatedRoom=roomService.addRoom(room);
    roomService.updateRoom(updatedRoom.getId(), updatedRoom);
    roomService.deleteRoom(updatedRoom.getId());
    hotelService.removeHotel(createdHotel.getId());
    userService.deleteUser(user.getUserId());
// 
    
}
@Test
void getRoomById() {
	SignupRequest userRequest = new SignupRequest("user2@example.com", "User2", "UserPassword", Role.USER);
    User user = authService.createUser(userRequest);

        HotelDTO hotel = new HotelDTO();
        hotel.setName("Test Hotel");
        hotel.setAddress("Test Address");
        hotel.setPhoneNo("1234567890");
        hotel.setDescription("Test Description");
        hotel.setAmenities("WiFi, Breakfast");
        hotel.setNoOfRooms(10);
        hotel.setImage("test.jpg");
        hotel.setUserId(user.getUserId());

        HotelDTO createdHotel=hotelService.addHotel(hotel);
    RoomDTO room = new RoomDTO();
    room.setHotelId(createdHotel.getId());
    room.setRoomSize("Large");
    room.setBedSize("king");
    room.setMaxOccupancy(6);
    room.setBaseFare(150.0);
    room.setAC(true);
    room.setAvailableFrom(LocalDate.now());
    room.setAvailableTo(LocalDate.now().plusDays(30));

    RoomDTO createdRoom=roomService.addRoom(room);
	roomService.getRoomById(createdRoom.getId());
	roomService.deleteRoom(createdRoom.getId());
    hotelService.removeHotel(createdHotel.getId());
    userService.deleteUser(user.getUserId());
}
@Test
void getAllRooms()
{
	roomService.getAllRooms();
}
@Test
void getAllRoomsByHotelId()
{	
	SignupRequest userRequest = new SignupRequest("user2@example.com", "User2", "UserPassword", Role.ADMIN);
    User user = authService.createUser(userRequest);

        HotelDTO hotel = new HotelDTO();
        hotel.setName("Test Hotel");
        hotel.setAddress("Test Address");
        hotel.setPhoneNo("1234567890");
        hotel.setDescription("Test Description");
        hotel.setAmenities("WiFi, Breakfast");
        hotel.setNoOfRooms(10);
        hotel.setImage("test.jpg");
        hotel.setUserId(user.getUserId());

        HotelDTO createdHotel=hotelService.addHotel(hotel);
	roomService.getAllRoomsByHotelId(createdHotel.getId());
	hotelService.removeHotel(createdHotel.getId());
	userService.deleteUser(user.getUserId());
}
@Test
void deleteRoom() {
	
	SignupRequest userRequest = new SignupRequest("user2@example.com", "User2", "UserPassword", Role.ADMIN);
    User user = authService.createUser(userRequest);
        HotelDTO hotel = new HotelDTO();
        hotel.setName("Test Hotel");
        hotel.setAddress("Test Address");
        hotel.setPhoneNo("1234567890");
        hotel.setDescription("Test Description");
        hotel.setAmenities("WiFi, Breakfast");
        hotel.setNoOfRooms(10);
        hotel.setImage("test.jpg");
        hotel.setUserId(user.getUserId());

        HotelDTO createdHotel=hotelService.addHotel(hotel);
    RoomDTO room = new RoomDTO();
    room.setHotelId(createdHotel.getId());
    room.setRoomSize("Large");
    room.setBedSize("king");
    room.setMaxOccupancy(6);
    room.setBaseFare(150.0);
    room.setAC(true);
    room.setAvailableFrom(LocalDate.now());
    room.setAvailableTo(LocalDate.now().plusDays(30));

    RoomDTO createdRoom=roomService.addRoom(room);
	roomService.deleteRoom(createdRoom.getId());
	hotelService.removeHotel(createdHotel.getId());
	userService.deleteUser(user.getUserId());
}

@Test
void createBooking_Success() {
	
	SignupRequest userRequest = new SignupRequest("user2@example.com", "User2", "UserPassword", Role.ADMIN);
    User user = authService.createUser(userRequest);
        HotelDTO hotel = new HotelDTO();
        hotel.setName("Test Hotel");
        hotel.setAddress("Test Address");
        hotel.setPhoneNo("1234567890");
        hotel.setDescription("Test Description");
        hotel.setAmenities("WiFi, Breakfast");
        hotel.setNoOfRooms(10);
        hotel.setImage("test.jpg");
        hotel.setUserId(user.getUserId());

        HotelDTO createdHotel=hotelService.addHotel(hotel);
    RoomDTO room = new RoomDTO();
    room.setHotelId(createdHotel.getId());
    room.setRoomSize("Large");
    room.setBedSize("king");
    room.setMaxOccupancy(6);
    room.setBaseFare(150.0);
    room.setAC(true);
    room.setAvailableFrom(LocalDate.now());
    room.setAvailableTo(LocalDate.now().plusDays(30));

    RoomDTO createdRoom=roomService.addRoom(room);
    
    SignupRequest signupRequest = new SignupRequest("john.doe@example.com", "John_Doe", "password123", Role.USER);
	User createdUser = authService.createUser(signupRequest);
	BookingCreateDTO bookingCreateDTO = new BookingCreateDTO();
    bookingCreateDTO.setUserId(createdUser.getUserId());
    bookingCreateDTO.setCheckInDate(LocalDate.of(2024, 9, 27));
    bookingCreateDTO.setCheckOutDate(LocalDate.of(2024, 10, 5));
    bookingCreateDTO.setNumberOfAdults(2);
    bookingCreateDTO.setNumberOfChildren(1);
    bookingCreateDTO.setGuestAges(Arrays.asList(30, 32, 5));
    bookingCreateDTO.setNumberOfRooms(1);
    bookingCreateDTO.setRoomIds(Arrays.asList(createdRoom.getId()));

    BookingResponseDTO bookingResponse = bookingService.createBooking(bookingCreateDTO);


}

@Test
void bookingsById()
{	SignupRequest userRequest = new SignupRequest("user2@example.com", "User2", "UserPassword", Role.ADMIN);
User user = authService.createUser(userRequest);

    HotelDTO hotel = new HotelDTO();
    hotel.setName("Test Hotel");
    hotel.setAddress("Test Address");
    hotel.setPhoneNo("1234567890");
    hotel.setDescription("Test Description");
    hotel.setAmenities("WiFi, Breakfast");
    hotel.setNoOfRooms(10);
    hotel.setImage("test.jpg");
    hotel.setUserId(user.getUserId());

    HotelDTO createdHotel=hotelService.addHotel(hotel);
RoomDTO room = new RoomDTO();
room.setHotelId(createdHotel.getId());
room.setRoomSize("Large");
room.setBedSize("king");
room.setMaxOccupancy(6);
room.setBaseFare(150.0);
room.setAC(true);
room.setAvailableFrom(LocalDate.now());
room.setAvailableTo(LocalDate.now().plusDays(30));

RoomDTO createdRoom=roomService.addRoom(room);

SignupRequest signupRequest = new SignupRequest("john.doe@example.com", "John_Doe", "password123", Role.USER);
User createdUser = authService.createUser(signupRequest);
BookingCreateDTO bookingCreateDTO = new BookingCreateDTO();
bookingCreateDTO.setUserId(createdUser.getUserId());
bookingCreateDTO.setCheckInDate(LocalDate.of(2024, 9, 27));
bookingCreateDTO.setCheckOutDate(LocalDate.of(2024, 10, 5));
bookingCreateDTO.setNumberOfAdults(2);
bookingCreateDTO.setNumberOfChildren(1);
bookingCreateDTO.setGuestAges(Arrays.asList(30, 32, 5));
bookingCreateDTO.setNumberOfRooms(1);
bookingCreateDTO.setRoomIds(Arrays.asList(createdRoom.getId()));

BookingResponseDTO bookingResponse = bookingService.createBooking(bookingCreateDTO);
	bookingService.getBookingById(bookingResponse.getBookingId());

}
@Test
void allBookings()
{
	bookingService.getAllBookings();
}
@Test
void allBookingsByUserId()
{	SignupRequest userRequest = new SignupRequest("user2@example.com", "User2", "UserPassword", Role.ADMIN);
User user = authService.createUser(userRequest);
	

	    HotelDTO hotel = new HotelDTO();
	    hotel.setName("Test Hotel");
	    hotel.setAddress("Test Address");
	    hotel.setPhoneNo("1234567890");
	    hotel.setDescription("Test Description");
	    hotel.setAmenities("WiFi, Breakfast");
	    hotel.setNoOfRooms(10);
	    hotel.setImage("test.jpg");
	    hotel.setUserId(user.getUserId());

	    HotelDTO createdHotel=hotelService.addHotel(hotel);
	RoomDTO room = new RoomDTO();
	room.setHotelId(createdHotel.getId());
	room.setRoomSize("Large");
	room.setBedSize("king");
	room.setMaxOccupancy(6);
	room.setBaseFare(150.0);
	room.setAC(true);
	room.setAvailableFrom(LocalDate.now());
	room.setAvailableTo(LocalDate.now().plusDays(30));

	RoomDTO createdRoom=roomService.addRoom(room);

	SignupRequest signupRequest = new SignupRequest("john.doe@example.com", "John_Doe", "password123", Role.USER);
	User createdUser = authService.createUser(signupRequest);
	BookingCreateDTO bookingCreateDTO = new BookingCreateDTO();
	bookingCreateDTO.setUserId(createdUser.getUserId());
	bookingCreateDTO.setCheckInDate(LocalDate.of(2024, 9, 27));
	bookingCreateDTO.setCheckOutDate(LocalDate.of(2024, 10, 5));
	bookingCreateDTO.setNumberOfAdults(2);
	bookingCreateDTO.setNumberOfChildren(1);
	bookingCreateDTO.setGuestAges(Arrays.asList(30, 32, 5));
	bookingCreateDTO.setNumberOfRooms(1);
	bookingCreateDTO.setRoomIds(Arrays.asList(createdRoom.getId()));

	BookingResponseDTO bookingResponse = bookingService.createBooking(bookingCreateDTO);
	bookingService.getBookingsByUser(createdUser.getUserId());
	bookingService.cancelBooking(bookingResponse.getBookingId());
    //roomService.deleteRoom(createdRoom.getId());
//    userService.deleteUser(createdUser.getUserId());
//    hotelService.removeHotel(createdHotel.getId());
//	hotelOwnerService.removeHotelOwner(createdHotelOwner.getOwnerId());
}
@Test
void cancelBooking()
{
	SignupRequest userRequest = new SignupRequest("user2@example.com", "User2", "UserPassword", Role.ADMIN);
	User user = authService.createUser(userRequest);

	    HotelDTO hotel = new HotelDTO();
	    hotel.setName("Test Hotel");
	    hotel.setAddress("Test Address");
	    hotel.setPhoneNo("1234567890");
	    hotel.setDescription("Test Description");
	    hotel.setAmenities("WiFi, Breakfast");
	    hotel.setNoOfRooms(10);
	    hotel.setImage("test.jpg");
	    hotel.setUserId(user.getUserId());

	    HotelDTO createdHotel=hotelService.addHotel(hotel);
	RoomDTO room = new RoomDTO();
	room.setHotelId(createdHotel.getId());
	room.setRoomSize("Large");
	room.setBedSize("king");
	room.setMaxOccupancy(6);
	room.setBaseFare(150.0);
	room.setAC(true);
	room.setAvailableFrom(LocalDate.now());
	room.setAvailableTo(LocalDate.now().plusDays(30));

	RoomDTO createdRoom=roomService.addRoom(room);

	SignupRequest signupRequest = new SignupRequest("john.doe@example.com", "John_Doe", "password123", Role.USER);
	User createdUser = authService.createUser(signupRequest);
	BookingCreateDTO bookingCreateDTO = new BookingCreateDTO();
	bookingCreateDTO.setUserId(createdUser.getUserId());
	bookingCreateDTO.setCheckInDate(LocalDate.of(2024, 9, 27));
	bookingCreateDTO.setCheckOutDate(LocalDate.of(2024, 10, 5));
	bookingCreateDTO.setNumberOfAdults(2);
	bookingCreateDTO.setNumberOfChildren(1);
	bookingCreateDTO.setGuestAges(Arrays.asList(30, 32, 5));
	bookingCreateDTO.setNumberOfRooms(1);
	bookingCreateDTO.setRoomIds(Arrays.asList(createdRoom.getId()));

	BookingResponseDTO bookingResponse = bookingService.createBooking(bookingCreateDTO);
	bookingService.getBookingsByUser(createdUser.getUserId());
	bookingService.cancelBooking(bookingResponse.getBookingId());
 
}}