package com.example.demo.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.HotelOwner;



@Repository
public interface HotelOwnerRepository extends JpaRepository<HotelOwner,Integer>{

	}



