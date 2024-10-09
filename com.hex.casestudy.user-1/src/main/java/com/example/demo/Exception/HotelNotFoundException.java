package com.example.demo.Exception;

public class HotelNotFoundException extends RuntimeException {
	
	public HotelNotFoundException()
	{
		super("Hotel does not exists");
	}
	public HotelNotFoundException(String message)
	{
		super(message);
	}
	

}
