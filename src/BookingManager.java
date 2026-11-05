import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;


/*
- Checks whether a facility is available.
- Prevents double-booking at the same date and time.
- Displays existing bookings.
 */
public class BookingManager {

    private ArrayList<Booking> bookings = new ArrayList<>();

    // Check whether a facility is already booked
    public boolean isFacilityAvailable(
            Facility facility,
            LocalDate date,
            LocalTime time) {

        for (Booking booking : bookings) {

            if (!booking.isCancelled()
                    && booking.getFacility().getFacilityCode()
                    .equals(facility.getFacilityCode())
                    && booking.getBookingDate().equals(date)
                    && booking.getBookingTime().equals(time)) {

                return false;
            }
        }

        return true;
    }

    // Add a new booking
    public void addBooking(Booking booking) {

        if (isFacilityAvailable(
                booking.getFacility(),
                booking.getBookingDate(),
                booking.getBookingTime())) {

            bookings.add(booking);
            System.out.println("Booking created successfully!");

        } else {
            System.out.println("This facility is already booked at that time.");
        }
    }

    // Display all bookings
    public void displayBookings() {

        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Booking booking : bookings) {
            booking.displayBooking();
        }
    }
}
