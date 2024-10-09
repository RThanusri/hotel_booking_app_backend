package com.example.demo.Exception;



public class HotelOwnerNotFoundException extends RuntimeException {
	
	public HotelOwnerNotFoundException(String message)
	{
		super(message);
	}
	public HotelOwnerNotFoundException()
	{
		super("Hotel Owner not found");
	}

}



