package com.example.demo.Exception;
public class UserNotFoundException extends RuntimeException{
		
		public  UserNotFoundException()
		{
			super(" User does not exists");
		}
		public  UserNotFoundException(String message)
		{
			super(message);
		}


	}


