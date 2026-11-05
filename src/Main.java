import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int choice;

        do {

            System.out.println("\n================================");
            System.out.println("       ACTIVEHUB SYSTEM");
            System.out.println("================================");

            System.out.println("1. Facility Booking Module");
            System.out.println("2. View Rental Catalogue");
            System.out.println("3. Create Rental Transaction");
            System.out.println("4. Apply Promotion");
            System.out.println("5. Make Payment");
            System.out.println("6. Daily Report");
            System.out.println("7. Exit");

            System.out.println("================================");

            System.out.print("Enter your choice: ");

            while (!input.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                input.next();
                System.out.print("Enter your choice: ");
            }

            choice = input.nextInt();

            switch (choice) {

                case 1:
                    System.out.println("\nFacility Booking Module");
					
                    break;

                case 2:
                    System.out.println("\nRental Catalogue");
                    break;

                case 3:
                    System.out.println("\nCreate Rental Transaction");
                    break;

                case 4:
                    System.out.println("\nPromotion Module");
                    break;

                case 5:
                    System.out.println("\nPayment Module");
                    break;

                case 6:
                    System.out.println("\nDaily Report");
                    break;

                case 7:
                    System.out.println("\nThank you for using ActiveHub!");
                    break;

                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }

        } while (choice != 7);

        input.close();
    }
}