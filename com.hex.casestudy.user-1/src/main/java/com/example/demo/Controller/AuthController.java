package com.example.demo.Controller;



	import java.util.Optional;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
	import org.springframework.security.authentication.AuthenticationManager;
	import org.springframework.security.authentication.BadCredentialsException;
	import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
	import org.springframework.security.core.Authentication;
	import org.springframework.security.core.context.SecurityContextHolder;
	import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;

	import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entities.AuthenticationRequest;
import com.example.demo.Entities.AuthenticationResponse;
import com.example.demo.Entities.JwtUtil;
import com.example.demo.Entities.SignupRequest;
import com.example.demo.Entities.User;
import com.example.demo.Exception.UserNotFoundException;
import com.example.demo.Service.AuthService;
import com.example.demo.Service.UserService;
import com.example.demo.dao.UserRepository;



@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
    private  AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<?> signupCustomer(@RequestBody SignupRequest signupRequest) {
	    try {
	        System.out.println("Signup request received: " + signupRequest);

	        if (authService.hasCustomerWithEmail(signupRequest.getEmail())) {
	            return new ResponseEntity<>("Customer already exists", HttpStatus.NOT_ACCEPTABLE);
	        }

	        User createdCustomerDto = authService.createUser(signupRequest);

	        if (createdCustomerDto == null) {
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }

	        return new ResponseEntity<>(createdCustomerDto, HttpStatus.CREATED);
	    } catch (Exception e) {
	        System.err.println("Error during signup: " + e.getMessage());
	        e.printStackTrace();
	        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            AuthenticationResponse response = authService.login(authenticationRequest);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); 
        }
	}
	

}