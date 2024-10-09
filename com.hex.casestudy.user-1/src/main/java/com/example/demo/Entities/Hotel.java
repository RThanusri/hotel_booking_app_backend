package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Size(max = 800, message = "Amenities description must be up to 255 characters")
    private String amenities;

    @Min(value = 1, message = "There must be at least 1 room")
    @Max(value = 1000, message = "The number of rooms cannot exceed 1000")
    private int noOfRooms;

    private String image;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Hotel() {}

    public Hotel(int id, String name, String address, String phoneNo, String description, String amenities, int noOfRooms, String image, User user) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNo = phoneNo;
        this.description = description;
        this.amenities = amenities;
        this.noOfRooms = noOfRooms;
        this.image = image;
        this.user = user;
    }

    // Getters and Setters
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

    // JsonGetter and JsonSetter for User
    @JsonGetter("user")
    public User getUser() {
        return user;
    }

    @JsonSetter("user")
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Hotel [id=" + id + ", name=" + name + ", address=" + address + ", phoneNo=" + phoneNo +
                ", description=" + description + ", amenities=" + amenities + ", noOfRooms=" + noOfRooms +
                ", image=" + image + ", user=" + user + "]";
    }
}