
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BookingManager {

    private ArrayList<Booking> bookings = new ArrayList<>();

    // Check whether a facility is available
    public boolean isFacilityAvailable(
            Facility facility,
            LocalDate date,
            LocalTime time,
            int durationHours) {

        LocalTime newEndTime = time.plusHours(durationHours);

        int i = 0;

        while (i < bookings.size()) {

            Booking booking = bookings.get(i);

            if (!booking.isCancelled()
                    && booking.getFacility().getFacilityCode()
                    .equals(facility.getFacilityCode())
                    && booking.getBookingDate().equals(date)) {

                LocalTime existingStart = booking.getBookingTime();

                LocalTime existingEnd = existingStart.plusHours(
                        booking.getDurationHours());

                // Check whether the time periods overlap
                if (time.isBefore(existingEnd)
                        && existingStart.isBefore(newEndTime)) {

                    return false;
                }
            }

            i++;
        }

        return true;
    }

    // Add a new booking
    public void addBooking(Booking booking) {

        if (isFacilityAvailable(
                booking.getFacility(),
                booking.getBookingDate(),
                booking.getBookingTime(),
                booking.getDurationHours())) {

            bookings.add(booking);
            saveBookings();

            System.out.println("Booking created successfully!");

        } else {
            MainUtils.displayRed("This facility is already booked during that time.");
        }
    }

    // Display all bookings
    public void displayBookings() {

        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        int i = 0;

		System.out.println("\n===== BOOKING DETAILS =====");
        while (i < bookings.size()) {
            Booking booking = bookings.get(i);
            booking.displayBooking();

            i++;
        }
    }

    // Cancel an existing booking
    public void cancelBooking(String bookingID) {

        int i = 0;

        while (i < bookings.size()) {

            Booking booking = bookings.get(i);

            if (booking.getBookingID().equals(bookingID)) {

                if (booking.isCancelled()) {
                    System.out.println("This booking is already cancelled.");

                } else {
                    booking.cancelBooking();
                    saveBookings();

                    System.out.println("Booking cancelled successfully!");
                }

                return;
            }

            i++;
        }

        System.out.println("Booking ID not found.");
    }

    // Save all bookings into a text file
    public void saveBookings() {

        try {

            File file = new File("../data/bookings.txt");

            file.getParentFile().mkdirs();

            FileWriter writer = new FileWriter(file);

            int i = 0;

            while (i < bookings.size()) {

                Booking booking = bookings.get(i);

                writer.write(
                        booking.getBookingID() + "|" +
                        booking.getCustomer().getName() + "|" +
                        booking.getCustomer().getContactNumber() + "|" +
                        booking.getFacility().getFacilityCode() + "|" +
                        booking.getBookingDate() + "|" +
                        booking.getBookingTime() + "|" +
                        booking.getParticipants() + "|" +
                        booking.getDurationHours() + "|" +
                        booking.getPackageType() + "|" +
                        booking.isCancelled() + "\n"
                );

                i++;
            }

            writer.close();

            System.out.println("Bookings saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving bookings: " + e.getMessage());
        }
    }

    // Load bookings from a text file
    public void loadBookings(ArrayList<Facility> facilities) {

        File file = new File("../data/bookings.txt");

        if (!file.exists()) {
            return;
        }

        try {

            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split("\\|", -1);

                if (data.length == 10 || data.length == 8) {

                    String bookingID = data[0];
                    String name = data[1];
                    String contact = data[2];
                    String facilityCode = data[3];

                    Facility selectedFacility = null;

                    int i = 0;

                    while (i < facilities.size()) {

                        if (facilities.get(i).getFacilityCode()
                                .equals(facilityCode)) {

                            selectedFacility = facilities.get(i);
                            break;
                        }

                        i++;
                    }

                    if (selectedFacility != null) {

                        int durationHours;
                        String packageType;
                        boolean cancelled;

                        if (data.length == 10) {

                            durationHours = Integer.parseInt(data[7]);
                            packageType = data[8];
                            cancelled = Boolean.parseBoolean(data[9]);

                        } else {

                            // Support older booking records
                            durationHours = 1;
                            packageType = "Normal";
                            cancelled = Boolean.parseBoolean(data[7]);
                        }

                        Customer customer = new Customer(name, contact);

                        Booking booking = new Booking(
                                bookingID,
                                customer,
                                selectedFacility,
                                LocalDate.parse(data[4]),
                                LocalTime.parse(data[5]),
                                Integer.parseInt(data[6]),
                                durationHours,
                                packageType
                        );

                        if (cancelled) {
                            booking.cancelBooking();
                        }

                        bookings.add(booking);
                    }
                }
            }

            reader.close();

            System.out.println("Previous bookings loaded.");

        } catch (IOException | RuntimeException e) {
            System.out.println("Error loading bookings: " + e.getMessage());
        }
    }

    // Get number of stored bookings
    public int getBookingCount() {
        return bookings.size();
    }

	// Find booking using Booking ID
	// If booking is cancelled or not exist it will return null
	public Booking findBooking(String bookingID) {

		int i = 0;

		while (i < bookings.size()) {

			Booking booking = bookings.get(i);

			if (booking.getBookingID().equalsIgnoreCase(bookingID) && !booking.isCancelled())
				return booking;
			i++;
		}
		return null;
	}
}
