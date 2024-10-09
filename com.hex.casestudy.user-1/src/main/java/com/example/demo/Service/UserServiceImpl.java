package com.example.demo.Service;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import com.example.demo.Entities.User;
import com.example.demo.dao.UserRepository;



@Service
public class UserServiceImpl implements UserService{
	@Autowired
    private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findFirstByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user; 
	}

	@Override
	public UserDetailsService userDetailsService() {

				return this;
	}
	

     
    	
    }
