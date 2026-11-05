import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputChecker {

    // Validate contact number
    public static String getValidContact(Scanner input) {

        String contact;

        while (true) {
            System.out.print("Enter valid contact number: (+60)");
            contact = input.nextLine();

            if (contact.matches("[0-9]{9,10}"))
                return contact;
            else
				MainUtils.displayError("Please enter 9 or 10 digits using numbers only.");
        }
    }

    // Get valid facility selection
    public static int getValidFacilityChoice(Scanner input, int totalFacilities) {

        int facilityChoice;

        while (true) {
            System.out.print(
                    "Select facility (1-" + totalFacilities + "): ");

            try {
                facilityChoice = Integer.parseInt(input.nextLine());

                if (facilityChoice >= 1
                        && facilityChoice <= totalFacilities) {

                    return facilityChoice;

                } else {
					MainUtils.displayError("Please enter 1 to 4");
                    System.out.println(totalFacilities + ".");
                }

            } catch (NumberFormatException e) {
				MainUtils.displayError("Please enter a number");
            }
        }
    }

	// Get valid booking date
	public static LocalDate getValidDate(Scanner input) {
		while (true) {
			System.out.print("Enter booking date (YYYY-MM-DD): ");
			try {
				LocalDate date = LocalDate.parse(input.nextLine());
				return date;
			} catch (DateTimeParseException e) {
				MainUtils.displayError("Please enter a valid date (YYYY-MM-DD).");
			}
		}
	}

	// Get valid booking time
	public static LocalTime getValidTime(Scanner input) {

		while (true) {

			System.out.print("Enter booking time (HH:MM): ");

			try {
				LocalTime time = LocalTime.parse(input.nextLine());

				return time;

			} catch (DateTimeParseException e) {
				MainUtils.displayError(
						"Please enter a valid time (HH:MM).");
			}
		}
	}

	// Get valid booking duration
	public static int getValidDuration(Scanner input) {
		while (true) {
			System.out.print("Enter duration (hours): ");

			try {
				int durationHours = Integer.parseInt(input.nextLine());
				if (durationHours > 0)
					return durationHours;
				MainUtils.displayRed("Duration must be greater than zero.");
			} catch (NumberFormatException e) {
				MainUtils.displayError("Please enter a valid number.");
			}
		}
	}

	// Get valid number of participants
	public static int getValidParticipants(Scanner input) {
		while (true) {
			System.out.print("Enter number of participants: ");

			try {
				int participants = Integer.parseInt(input.nextLine());
				if (participants > 0)
					return participants;
				MainUtils.displayRed("Participants must be greater than zero.");
			} catch (NumberFormatException e) {
				MainUtils.displayError("Please enter a valid number.");
			}
		}
	}
	
	// Get valid package selection
	public static String getValidPackageType(Scanner input) {

		while (true) {
			System.out.println("\n===== SELECT PACKAGE =====");
			System.out.println("0. No Package - RM0.00");
			System.out.println("1. Normal Package - RM14.00");
			System.out.println("2. Premium Package - RM28.00");
			System.out.print("Select package: ");

			try {
				int packageChoice = Integer.parseInt(input.nextLine());

				if (packageChoice == 0) {
					return "None";

				} else if (packageChoice == 1) {
					return "Normal";

				} else if (packageChoice == 2) {
					return "Premium";

				} else {
					MainUtils.displayError("Please enter 0, 1 or 2.");
				}

			} catch (NumberFormatException e) {
				MainUtils.displayError("Please enter 0, 1 or 2.");
			}
		}
	}

}
