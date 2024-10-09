package com.example.demo.dao;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.example.demo.Entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	    User findByEmail(String email);

	    Optional<User> findFirstByEmail(String email);
	    
	    @Query(value = "SELECT * FROM user WHERE role = :role", nativeQuery = true)
	    List<User> findAllByRole(@Param ("role")String role);

		User findByUserName(String userName);
		
	}