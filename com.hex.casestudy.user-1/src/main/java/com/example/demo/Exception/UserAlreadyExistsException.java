package com.example.demo.Exception;



public class UserAlreadyExistsException extends RuntimeException {
		
		public UserAlreadyExistsException()
		{
			super("User Already Exists");
		}
		public UserAlreadyExistsException(String message)
		{
			super(message);
		}


	}


