package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.User;
import com.example.demo.Exception.UserAlreadyExistsException;
import com.example.demo.Exception.UserNotFoundException;
import com.example.demo.dao.UserRepository;

import jakarta.validation.Valid;
@Service
public class UserServices {
	 @Autowired
	    private UserRepository userRepository;
	 @Autowired
		private PasswordEncoder passwordEncoder;
	   

	    private void validatePassword(String password) {
	        
	        if (password == null || !password.matches("^(?=.[a-z])(?=.[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
	            throw new IllegalArgumentException("Password does not meet requirements");
	        }
	    }

	 

	    public String updatePassword(String email, String newPassword) {
	    	validatePassword(newPassword);
	        User user = userRepository.findByEmail(email);
	        if (user == null) {
	            throw new UserNotFoundException();
	        } else {
	            user.setPassword(passwordEncoder.encode(newPassword));
	            userRepository.save(user);
	            return "Password Updated Successfully";
	        }
	    }

	    public User getUserId(Long userId) {
	        Optional<User> user = userRepository.findById(userId);
	        if (user.isEmpty()) {
	            throw new UserNotFoundException();
	        } else {
	            return user.get();
	        }
	    }

	    public List<User> getAllUser() {
	        return userRepository.findAll();
	    }

	    public String deleteUser(Long userId) {
	        Optional<User> s = userRepository.findById(userId);
	        if (s.isEmpty()) {
	            throw new UserNotFoundException();
	        } else {
	            userRepository.deleteById(userId);
	            return "User Deleted Successfully";
	        }
	}
	    
	   
	    public List<User> getUsersByRole(String role) {
	        return userRepository.findAllByRole(role);
	    }
	   

	

}