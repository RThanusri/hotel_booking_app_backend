package com.example.demo.Exception;

public class BookingIdNotFoundException extends RuntimeException {
	public BookingIdNotFoundException ()
	{
		super("Booking id provided is bot available");
		
	}
	public BookingIdNotFoundException (String message)
	{
		super(message);
	}

}
