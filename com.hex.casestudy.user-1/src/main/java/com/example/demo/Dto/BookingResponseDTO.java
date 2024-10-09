package com.example.demo.Dto;



public class BookingResponseDTO {

	    private Long bookingId;
	    private double totalFare;
	    private String bookingStatus;
	    private BookingDTO bookingDetails;

	   
	    public Long getBookingId() {
	        return bookingId;
	    }

	    public void setBookingId(Long bookingId) {
	        this.bookingId = bookingId;
	    }

	    public double getTotalFare() {
	        return totalFare;
	    }

	    public void setTotalFare(double totalFare) {
	        this.totalFare = totalFare;
	    }

	    public String getBookingStatus() {
	        return bookingStatus;
	    }

	    public void setBookingStatus(String bookingStatus) {
	        this.bookingStatus = bookingStatus;
	    }

	    public BookingDTO getBookingDetails() {
	        return bookingDetails;
	    }

	    public void setBookingDetails(BookingDTO bookingDetails) {
	        this.bookingDetails = bookingDetails;
	    }
	}


