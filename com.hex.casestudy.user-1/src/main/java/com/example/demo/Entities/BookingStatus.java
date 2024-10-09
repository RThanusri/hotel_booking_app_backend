package com.example.demo.Entities;

public enum BookingStatus {
	
	    PENDING("Pending"),
	    CONFIRMED("Confirmed"),
	    CHECKED_IN("Checked In"),
	    COMPLETED("Completed"),
	    CANCELED("Canceled"),
	    UPDATED("Updated");

	    private final String status;

	    BookingStatus(String status) {
	        this.status = status;
	    }

	    public String getStatus() {
	        return status;
	    }

	    @Override
	    public String toString() {
	        return this.status;
	    }
	}



