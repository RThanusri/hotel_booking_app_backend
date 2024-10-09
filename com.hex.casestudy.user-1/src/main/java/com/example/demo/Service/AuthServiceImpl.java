package com.example.demo.Service;


	import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
	import org.springframework.stereotype.Service;

import com.example.demo.Entities.AuthenticationRequest;
import com.example.demo.Entities.AuthenticationResponse;
import com.example.demo.Entities.JwtUtil;
import com.example.demo.Entities.SignupRequest;
import com.example.demo.Entities.User;
import com.example.demo.Enum.Role;
import com.example.demo.dao.UserRepository;


@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserService userService;
	@Autowired
	private JwtUtil jwtUtil;

	public User createUser(SignupRequest signupRequest) {
		User user = new User();
		user.setUserName(signupRequest.getName());
		user.setEmail(signupRequest.getEmail());
		user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));

		if (signupRequest.getRole() == null) {
			user.setRole(Role.USER);
		} else {
			user.setRole(signupRequest.getRole());
		}

		User createdUser = userRepository.save(user);

		User userDto = new User();
		userDto.setUserId(createdUser.getUserId());
		userDto.setUserName(createdUser.getUserName());
		userDto.setPassword(createdUser.getPassword());
		userDto.setEmail(createdUser.getEmail());
		userDto.setRole(createdUser.getRole());

		return userDto;
	}

	public boolean hasCustomerWithEmail(String email) {
		return userRepository.findFirstByEmail(email).isPresent();
	}

	public AuthenticationResponse login(AuthenticationRequest authRequest) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(),
					authRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect Email Or Password.");
		}

		UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authRequest.getEmail());
		String jwt = jwtUtil.generateToken(userDetails);

		AuthenticationResponse response = new AuthenticationResponse();
		Optional<User> user = userRepository.findFirstByEmail(userDetails.getUsername());
		if (user.isPresent()) {
			response.setJwt(jwt);
			response.setUserId(user.get().getUserId());
			response.setRole(user.get().getRole());
		}
		return response;
	}
}

