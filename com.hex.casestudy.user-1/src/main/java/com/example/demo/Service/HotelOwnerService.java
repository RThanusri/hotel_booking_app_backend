package com.example.demo.Service;



	import java.util.List;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;

import com.example.demo.Entities.HotelOwner;
import com.example.demo.Exception.HotelOwnerNotFoundException;
import com.example.demo.dao.HotelOwnerRepository;



	@Service
	public class HotelOwnerService {

		@Autowired
		HotelOwnerRepository hotelOwnerRepository;

		public HotelOwner addHotelOwner(HotelOwner s) {
			HotelOwner s2=hotelOwnerRepository.save(s);
			
			return s2;
		}

		public String removeHotelOwner(int ownerID) {
			if(hotelOwnerRepository.findById(ownerID) != null) {
				hotelOwnerRepository.deleteById(ownerID);
				return "HotelOwner removed";
			}
			else {
				return null;
			}
		}
		public String updateHotelOwner(int hotelOwnerId, HotelOwner hotelOwner) {
		    HotelOwner h = hotelOwnerRepository.findById(hotelOwnerId)
		            .orElseThrow(() -> new HotelOwnerNotFoundException("HotelOwner with ID " + hotelOwnerId + " not found"));

		    if (hotelOwner.getAddress() != null) {
		        h.setAddress(hotelOwner.getAddress());
		    }
		    if (hotelOwner.getName() != null) {
		        h.setName(hotelOwner.getName());
		    }
		    if (hotelOwner.getPhoneNo() != null) {
		        h.setPhoneNo(hotelOwner.getPhoneNo());
		    }

		    hotelOwnerRepository.save(h);
		    return "Hotel owner details are updated successfully";
		}

		public List<HotelOwner> getAllHotelOwners() {
			List<HotelOwner> h = hotelOwnerRepository.findAll();
			return h;
		}

		public HotelOwner getHotelOwnerById(int id) {
			HotelOwner h = hotelOwnerRepository.findById(id).orElse(null) ;
			
			if(h!= null) {
				return h;
			}
			else {
				return null;
			}
		}
		
		
	}



