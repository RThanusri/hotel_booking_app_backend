package com.example.demo.Dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class HotelDTO {

    private int id;

    @NotEmpty(message = "Hotel name cannot be empty")
    @Size(min = 3, max = 100, message = "Hotel name must be between 3 and 100 characters")
    private String name;

    @NotEmpty(message = "Hotel address cannot be empty")
    @Size(min = 3, max = 255, message = "Hotel address must be between 3 and 255 characters")
    private String address;

    @NotEmpty(message = "Hotel phone number cannot be empty")
    private String phoneNo;

    @Size(max = 500, message = "Hotel description must be up to 500 characters")
    private String description;

    @Size(max = 255, message = "Amenities description must be up to 255 characters")
    private String amenities;

    @Min(value = 1, message = "There must be at least 1 room")
    @Max(value = 1000, message = "The number of rooms cannot exceed 1000")
    private int noOfRooms;

    private String image;

    
    private Long userId;

    
    public HotelDTO() {}

   
    public HotelDTO(int id,
                    @NotEmpty(message = "Hotel name cannot be empty") 
                    @Size(min = 3, max = 100, message = "Hotel name must be between 3 and 100 characters") 
                    String name,
                    @NotEmpty(message = "Hotel address cannot be empty") 
                    @Size(min = 3, max = 255, message = "Hotel address must be between 3 and 255 characters") 
                    String address,
                    @NotEmpty(message = "Hotel phone number cannot be empty") 
                    String phoneNo,
                    @Size(max = 500, message = "Hotel description must be up to 500 characters") 
                    String description,
                    @Size(max = 255, message = "Amenities description must be up to 255 characters") 
                    String amenities,
                    @Min(value = 1, message = "There must be at least 1 room") 
                    @Max(value = 1000, message = "The number of rooms cannot exceed 1000") 
                    int noOfRooms,
                    String image,
                    Long userId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNo = phoneNo;
        this.description = description;
        this.amenities = amenities;
        this.noOfRooms = noOfRooms;
        this.image = image;
        this.userId = userId;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public int getNoOfRooms() {
        return noOfRooms;
    }

    public void setNoOfRooms(int noOfRooms) {
        this.noOfRooms = noOfRooms;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "HotelDTO [id=" + id + ", name=" + name + ", address=" + address + ", phoneNo=" + phoneNo +
               ", description=" + description + ", amenities=" + amenities + ", noOfRooms=" + noOfRooms +
               ", image=" + image + ", userId=" + userId + "]";
    }
}
