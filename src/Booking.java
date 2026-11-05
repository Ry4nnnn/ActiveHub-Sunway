package ActiveHubSportCentre.src;

import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {

    private String bookingID;
    private Customer customer;
    private Facility facility;

    private LocalDate bookingDate;
    private LocalTime bookingTime;

    private int participants;
    private boolean cancelled;

    // Constructor
    public Booking(String bookingID, Customer customer,
                   Facility facility, LocalDate bookingDate,
                   LocalTime bookingTime, int participants) {

        this.bookingID = bookingID;
        this.customer = customer;
        this.facility = facility;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.participants = participants;
        this.cancelled = false;
    }

    // Getters
    public String getBookingID() {
        return bookingID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Facility getFacility() {
        return facility;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public LocalTime getBookingTime() {
        return bookingTime;
    }

    public int getParticipants() {
        return participants;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    // Cancel booking
    public void cancelBooking() {
        cancelled = true;
    }

    // Display booking details
    public void displayBooking() {

        System.out.println("\n===== BOOKING DETAILS =====");

        System.out.println("Booking ID: " + bookingID);
        System.out.println("Customer: " + customer.getName());
        System.out.println("Contact: " + customer.getContactNumber());
        System.out.println("Facility: " + facility.getFacilityName());
        System.out.println("Date: " + bookingDate);
        System.out.println("Time: " + bookingTime);
        System.out.println("Participants: " + participants);
		if (cancelled == true)
			System.out.println("Status: " + ("Cancelled"));
		else
			System.out.println("Status: " + ("Active"));
			
    }
}