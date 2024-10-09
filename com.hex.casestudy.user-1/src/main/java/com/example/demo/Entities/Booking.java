package com.example.demo.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate;

    @Column(name = "check_out_date", nullable = false)
    private LocalDate checkOutDate;

    @Column(name = "number_of_adults", nullable = false)
    private int numberOfAdults;

    @Column(name = "number_of_children", nullable = false)
    private int numberOfChildren;

    @ElementCollection
    @CollectionTable(name = "guest_ages", joinColumns = @JoinColumn(name = "booking_id"))
    @Column(name = "age")
    private List<Integer> guestAges; 

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookingStatus status;
    @Column(nullable = false)
    private Integer  numberOfRooms;
    
    @ManyToMany
    @JoinTable(
        name = "booking_room",
        joinColumns = @JoinColumn(name = "booking_id"),
        inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    private List<Room> rooms;
public Booking()
{
	
}
	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDate getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}

	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public int getNumberOfAdults() {
		return numberOfAdults;
	}

	public void setNumberOfAdults(int numberOfAdults) {
		this.numberOfAdults = numberOfAdults;
	}

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

	public List<Integer> getGuestAges() {
		return guestAges;
	}

	public void setGuestAges(List<Integer> guestAges) {
		this.guestAges = guestAges;
	}

	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;
	}

	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public Booking(Long bookingId, User user, LocalDate checkInDate, LocalDate checkOutDate, int numberOfAdults,
			int numberOfChildren, List<Integer> guestAges, BookingStatus status, int numberOfRooms, List<Room> rooms) {
		super();
		this.bookingId = bookingId;
		this.user = user;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.numberOfAdults = numberOfAdults;
		this.numberOfChildren = numberOfChildren;
		this.guestAges = guestAges;
		this.status = status;
		this.numberOfRooms = numberOfRooms;
		this.rooms = rooms;
	}

	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", user=" + user + ", checkInDate=" + checkInDate + ", checkOutDate="
				+ checkOutDate + ", numberOfAdults=" + numberOfAdults + ", numberOfChildren=" + numberOfChildren
				+ ", guestAges=" + guestAges + ", status=" + status + ", numberOfRooms=" + numberOfRooms + ", rooms="
				+ rooms + "]";
	}

}