
import java.util.ArrayList;

public class MainUtils {

    // Display available facilities
    public static void displayFacilities(ArrayList<Facility> facilities) {

        System.out.println("\nAvailable Facilities:");

        int i = 0;
        while (i < facilities.size()) {
            System.out.printf("%d. %s - RM%.2f/hour%n", i + 1,
				facilities.get(i).getFacilityName(),
				facilities.get(i).getRentalPrice());
            i++;
        }
    }

	// Display main menu
	public static void displayMainMenu() {

		System.out.println("\n===== ACTIVEHUB SPORTS CENTRE =====");
		System.out.println("1. Facility Booking");
		System.out.println("2. View Bookings");
		System.out.println("3. View Rental Catalogue");
		System.out.println("4. Create Rental Transaction");
		System.out.println("5. Make Payment");
		System.out.println("6. Daily Report");
		System.out.println("7. Cancel Booking");
		System.out.println("0. Exit");
	}

	// Display error message in red with preset
	public static void displayError(String errorMessage) {
		System.out.println("\u001B[31mInvalid Input! " + errorMessage + "\u001B[0m");
	}

	// Display error message in red
	public static void displayRed(String errorMessage) {
		System.out.println("\u001B[31m" + errorMessage + "\u001B[0m");
	}

}
