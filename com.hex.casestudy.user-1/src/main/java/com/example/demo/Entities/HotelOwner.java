package com.example.demo.Entities;



	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	import jakarta.persistence.Table;
	import jakarta.validation.constraints.NotEmpty;
	import jakarta.validation.constraints.Pattern;
	import jakarta.validation.constraints.Size;

	@Entity
	@Table
	public class HotelOwner {
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int ownerId;

	    @NotEmpty(message = "Name cannot be empty")
	    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
	    private String name;

	    @NotEmpty(message = "Address cannot be empty")
	    @Size(min = 10, max = 255, message = "Address must be between 10 and 255 characters")
	    private String address;

	    @NotEmpty(message = "Phone number cannot be empty")
	    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
	    private String phoneNo;
	    
	    public HotelOwner(){
	    	
	    }
	    
		public HotelOwner(int ownerId,
				@NotEmpty(message = "Name cannot be empty") @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters") String name,
				@NotEmpty(message = "Address cannot be empty") @Size(min = 10, max = 255, message = "Address must be between 10 and 255 characters") String address,
				@NotEmpty(message = "Phone number cannot be empty") @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits") String phoneNo) {
			super();
			this.ownerId = ownerId;
			this.name = name;
			this.address = address;
			this.phoneNo = phoneNo;
		}

		public HotelOwner(int ownerId) {
	        this.ownerId = ownerId;
	    }

		public int getOwnerId() {
			return ownerId;
		}

		public void setOwnerId(int ownerId) {
			this.ownerId = ownerId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getPhoneNo() {
			return phoneNo;
		}

		public void setPhoneNo(String phoneNo) {
			this.phoneNo = phoneNo;
		}

		@Override
		public String toString() {
			return "HotelOwner [ownerId=" + ownerId + ", name=" + name + ", address=" + address + ", phoneNo=" + phoneNo
					+ "]";
		}
	    
	    
		
	}



