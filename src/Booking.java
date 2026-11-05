
import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {

    private String bookingID;
    private Customer customer;
    private Facility facility;

    private LocalDate bookingDate;
    private LocalTime bookingTime;

    private int participants;
    private int durationHours;
    private String packageType;
    private boolean cancelled;

    // Constructor
    public Booking(String bookingID, Customer customer,
                   Facility facility, LocalDate bookingDate,
                   LocalTime bookingTime, int participants,
                   int durationHours, String packageType) {

        this.bookingID = bookingID;
        this.customer = customer;
        this.facility = facility;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.participants = participants;
        this.durationHours = durationHours;
        this.packageType = packageType;
        this.cancelled = false;
    }

    // Getters
    public String getBookingID() { return bookingID; }
    public Customer getCustomer() { return customer; }
    public Facility getFacility() { return facility; }
    public LocalDate getBookingDate() { return bookingDate; }
    public LocalTime getBookingTime() { return bookingTime; }
    public int getParticipants() { return participants; }
    public int getDurationHours() { return durationHours; }
    public String getPackageType() { return packageType; }
    public boolean isCancelled() { return cancelled; }

    // Set package price
    public double calculatePackagePrice() {

        if (packageType.equalsIgnoreCase("Normal")) {
            return 14.00;

        } else if (packageType.equalsIgnoreCase("Premium")) {
            return 28.00;
        }
        return 0.00;
    }

    // Calculate total booking price
    public double calculateBookingPrice() {

        double courtPrice =
                facility.getRentalPrice() * durationHours;

        return courtPrice + calculatePackagePrice();
    }

    public void cancelBooking() {
        cancelled = true;
    }

    // Display booking details
    public void displayBooking() {
        System.out.println("Booking ID: " + bookingID);
        System.out.println("Customer: " + customer.getName());
        System.out.println("Contact: " + customer.getContactNumber());
        System.out.println("Facility: " + facility.getFacilityName());
        System.out.println("Date: " + bookingDate);
        System.out.println("Time: " + bookingTime);
        System.out.println("Duration: " + durationHours + " hour(s)");
        System.out.println("Participants: " + participants);
        System.out.println("Package: " + packageType);

        System.out.printf("Court Price: RM%.2f%n",
                facility.getRentalPrice() * durationHours);

        System.out.printf("Package Price: RM%.2f%n",
                calculatePackagePrice());

        System.out.printf("Total Booking Price: RM%.2f%n",
                calculateBookingPrice());

        if (cancelled) {
            System.out.println("Status: Cancelled");
        } else {
            System.out.println("Status: Active");
        }
        System.out.println("---------------------------\n");
    }
}
