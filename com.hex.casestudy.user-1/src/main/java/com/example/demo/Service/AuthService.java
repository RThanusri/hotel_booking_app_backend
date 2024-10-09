package com.example.demo.Service;

import com.example.demo.Entities.AuthenticationRequest;
import com.example.demo.Entities.AuthenticationResponse;
import com.example.demo.Entities.SignupRequest;
import com.example.demo.Entities.User;

public interface AuthService {
	User createUser(SignupRequest signupRequest);

	boolean hasCustomerWithEmail(String email);

	AuthenticationResponse login(AuthenticationRequest authRequest);

	
}

		
	



