package com.example.demo.Exception;

public class RoomNotFoundException extends RuntimeException {
	
	public RoomNotFoundException(String message)
	{
		super(message);
	}
	public RoomNotFoundException()
	{
		super("Room not Found ");
	}

}
