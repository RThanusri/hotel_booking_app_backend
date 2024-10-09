package com.example.demo.Exception;

public class NoContentFoundException extends RuntimeException {
	
	public NoContentFoundException(String message)
	{
		super(message);
	}
	public NoContentFoundException()
	{
		super("No such data exists");
	}

}
