package com.example.demo.Dto;



	import java.time.LocalDate;
	import java.util.List;

	public class BookingDTO {

	    private Long bookingId;
	    private LocalDate checkInDate;
	    private LocalDate checkOutDate;
	    private int numberOfAdults;
	    private int numberOfChildren;
	    private int numberOfRooms;  
	    private List<Long> roomIds; 


	    public Long getBookingId() {
	        return bookingId;
	    }

	    public void setBookingId(Long bookingId) {
	        this.bookingId = bookingId;
	    }

	    public LocalDate getCheckInDate() {
	        return checkInDate;
	    }

	    public void setCheckInDate(LocalDate checkInDate) {
	        this.checkInDate = checkInDate;
	    }

	    public LocalDate getCheckOutDate() {
	        return checkOutDate;
	    }

	    public void setCheckOutDate(LocalDate checkOutDate) {
	        this.checkOutDate = checkOutDate;
	    }

	    public int getNumberOfAdults() {
	        return numberOfAdults;
	    }

	    public void setNumberOfAdults(int numberOfAdults) {
	        this.numberOfAdults = numberOfAdults;
	    }

	    public int getNumberOfChildren() {
	        return numberOfChildren;
	    }

	    public void setNumberOfChildren(int numberOfChildren) {
	        this.numberOfChildren = numberOfChildren;
	    }

	    public int getNumberOfRooms() {
	        return numberOfRooms;
	    }

	    public void setNumberOfRooms(int numberOfRooms) {
	        this.numberOfRooms = numberOfRooms;
	    }

	    public List<Long> getRoomIds() {
	        return roomIds;
	    }

	    public void setRoomIds(List<Long> roomIds) {
	        this.roomIds = roomIds;
	    }
	}


