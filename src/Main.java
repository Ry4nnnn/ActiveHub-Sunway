import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Main {

    static Scanner input = new Scanner(System.in);
    static BookingManager bookingManager = new BookingManager();
    static ArrayList<Facility> facilities = new ArrayList<>();
    static RentalManager rentalManager = new RentalManager();
    static ArrayList<Payment> payments = new ArrayList<>();
    static TransactionManager transactionManager = new TransactionManager();
    static PromotionManager promotionManager = new PromotionManager();

    static int nextTransactionID = 1;
    static int nextBookingID = 1;
    static int nextPaymentID = 1;

    public static void main(String[] args) {

        // Create sample facilities
        facilities.add(new Facility("A01", "Badminton Court 1", 20.00));
        facilities.add(new Facility("A02", "Badminton Court 2", 20.00));
        facilities.add(new Facility("A03", "Basketball Court 1", 40.00));
        facilities.add(new Facility("A04", "Futsal Court 1", 35.00));
        
        //gets booking and transaction details from the txt file
        bookingManager.loadBookings(facilities);
        transactionManager.loadTransactions(bookingManager, rentalManager);
        
        //set the next booking id +1
        nextBookingID = bookingManager.getBookingCount() + 1;
        nextTransactionID = transactionManager.getTransactionCount() + 1;

        int choice = 99;
        while (choice != 0) {

            MainUtils.displayMainMenu();
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(input.nextLine());

                switch (choice) {
                    case 0:
                        System.out.println("Thank you for using ActiveHub!");
                        break;

                    case 1:
                        createBooking();
                        break;

                    case 2:
                        bookingManager.displayBookings();
                        break;

                    case 3:
                        rentalManager.displayCatalogue();
                        break;

                    case 4:
                        createRentalTransaction();
                        break;

                    case 5:
                        makePayment();
                        break;

                    case 6:
                        Report.displayDailyReport(bookingManager, transactionManager);
                        break;

                    case 7:
                        System.out.print("Enter Booking ID to cancel: ");
                        String bookingID = input.nextLine();
                        bookingManager.cancelBooking(bookingID);
                        break;

                    default:
                        System.out.println("Invalid. Try again.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        input.close();
    }

    //create new booking
    public static void createBooking() {

        System.out.println("\n===== FACILITY BOOKING =====");

        System.out.print("Enter customer name: ");
        String name = input.nextLine();
        String contact = InputChecker.getValidContact(input);
        Customer customer = new Customer(name, contact);

        MainUtils.displayFacilities(facilities);

        int facilityChoice = InputChecker.getValidFacilityChoice(input, facilities.size());
        Facility selectedFacility = facilities.get(facilityChoice - 1);

        try {
            LocalDate date = InputChecker.getValidDate(input);
            LocalTime time = InputChecker.getValidTime(input);
            int durationHours = InputChecker.getValidDuration(input);
            int participants = InputChecker.getValidParticipants(input);
            String packageType = InputChecker.getValidPackageType(input);
            String bookingID = String.format("B%03d", nextBookingID);

            Booking booking = new Booking(
                    bookingID,
                    customer,
                    selectedFacility,
                    date,
                    time,
                    participants,
                    durationHours,
                    packageType
            );

            if (bookingManager.isFacilityAvailable(
                    selectedFacility, date, time, durationHours)) {

                bookingManager.addBooking(booking);
                nextBookingID++;

                System.out.printf("Booking Price: RM%.2f%n",booking.calculateBookingPrice());

            } else {
                MainUtils.displayRed("This facility is already booked at that date and time.");
            }

        } catch (DateTimeParseException e) {
            MainUtils.displayRed("Invalid date or time format.");

        } catch (NumberFormatException e) {
            MainUtils.displayError("Please enter a valid number.");
        }
    }

    // Create new rental transaction
    public static void createRentalTransaction() {

        System.out.println("\n===== CREATE RENTAL TRANSACTION =====");

        System.out.print("Enter customer name: ");
        String name = input.nextLine();
        String contact = InputChecker.getValidContact(input);
        Customer customer = new Customer(name, contact);
        Booking selectedBooking = null;

        while (true) {
            System.out.println("\n1. Existing Facility Booking");
            System.out.println("2. Walk-in Customer");
            System.out.print("Select transaction type: ");

            String bookingChoice = input.nextLine();

            if (bookingChoice.equals("1")) {

                System.out.print("Enter Booking ID: ");
                String bookingID = input.nextLine();

                selectedBooking = bookingManager.findBooking(bookingID);
                if (selectedBooking == null) {
                    MainUtils.displayRed("Booking ID not found or booking is cancelled.");
                    continue;
                }
                break;
            }
            else if (bookingChoice.equals("2"))
                break;
            else
                MainUtils.displayError("Please enter 1 or 2.");
        }

        String transactionID = String.format("T%03d", nextTransactionID);

        RentalTransaction transaction = new RentalTransaction(transactionID, customer, selectedBooking);

        rentalManager.displayCatalogue();

        while (true) {

            System.out.print("\nEnter item code [ Checkout(C) | Update(U) | Exit(E) ]: ");
            String itemCode = input.nextLine();

            if (itemCode.equalsIgnoreCase("E") || itemCode.equalsIgnoreCase("exit")) {
                MainUtils.displayRed("Rental transaction cancelled.\n");
                return;
            }
            if (itemCode.equalsIgnoreCase("C") || itemCode.equalsIgnoreCase("checkout")) {
                break;
            }
            if (itemCode.equalsIgnoreCase("U") || itemCode.equalsIgnoreCase("update")) {
                System.out.print("Enter item code to update: ");
                String updateCode = input.nextLine();
                RentalItem updateItem = rentalManager.findItem(updateCode);

                if (updateItem == null) {
                    MainUtils.displayError("Invalid item code!");
                    continue;
                }
                if (!transaction.containsItem(updateItem)) {
                    MainUtils.displayRed("This item is not in your cart!");
                    continue;
                }
                System.out.print("Enter new quantity (0 to remove): ");

                try {
                    int newQuantity = Integer.parseInt(input.nextLine());

                    if (newQuantity < 0) {
                        MainUtils.displayRed("Quantity cannot be negative.");
                        continue;
                    }
                    transaction.updateItemQuantity(updateItem, newQuantity);
                }
                catch (NumberFormatException e) {
                    MainUtils.displayError("Please enter a valid quantity.");
                }
                continue;
            }

            RentalItem item = rentalManager.findItem(itemCode);

            if (item == null) {
                MainUtils.displayRed("Invalid item code!");
                continue;
            }

            System.out.print("Enter quantity: ");

            try {
                int quantity = Integer.parseInt(input.nextLine());

                if (quantity <= 0) {
                    System.out.println("Quantity must be greater than zero.");
                    continue;
                }

                transaction.addItem(item, quantity);

            } catch (NumberFormatException e) {
                MainUtils.displayError("Please enter a valid quantity.");
            }
        }

        if (transaction.calculateSubtotal() == 0) {
            MainUtils.displayRed("No items added. Transaction cancelled.");
            return;
        }

        transactionManager.addTransaction(transaction);

        transactionManager.saveTransactions();

        nextTransactionID++;

        transaction.displayTransaction(promotionManager);
    }

    // Make payment
    public static void makePayment() {

        System.out.println("\n===== MAKE PAYMENT =====");
        System.out.println("1. Facility Booking");
        System.out.println("2. Rental Transaction");
        System.out.println("0. Exit");

        System.out.print("Select payment type: ");
        String choice = input.nextLine();

        if (choice.equals("0"))
            return;
        else if (choice.equals("1"))
            payBooking();
        else if (choice.equals("2"))
            payRentalTransaction();
        else
            MainUtils.displayError("Please enter 0, 1, or 2.");
    }

    // Select payment method
    public static Payment selectPaymentMethod(
            String referenceID, double amount) {

        while (true) {

            System.out.println("\n===== PAYMENT METHOD =====");
            System.out.println("1. Cash");
            System.out.println("2. Credit/Debit Card");
            System.out.println("3. E-Wallet");
            System.out.println("0. Cancel Payment");

            System.out.print("Select payment method: ");
            String choice = input.nextLine();

            if (choice.equals("0")) {
                MainUtils.displayRed("Payment cancelled.");
                return null;
            }

            String paymentID = String.format("P%03d", nextPaymentID);

            if (choice.equals("1"))
                return new PaymentCash(paymentID, referenceID, amount);
            else if (choice.equals("2"))
                return new PaymentCard(paymentID, referenceID, amount);
            else if (choice.equals("3"))
                return new PaymentEWallet(paymentID, referenceID, amount);
            else
                MainUtils.displayError("Please enter 0, 1, 2, or 3.");
        }
    }

    // Pay for a facility booking
    public static void payBooking() {

        System.out.println("\n===== BOOKING PAYMENT =====");
        System.out.print("Enter Booking ID: ");

        String bookingID = input.nextLine();
        Booking booking = bookingManager.findBooking(bookingID);

        // Check whether booking exists
        if (booking == null) {
            MainUtils.displayRed("Booking not found or booking is cancelled.");
            return;
        }

        // Check whether already paid
        if (booking.isPaid()) {
            MainUtils.displayRed("This booking has already been paid.");
            return;
        }

        // Calculate booking amount
        double amount = booking.calculateBookingPrice();

        System.out.printf("Amount to pay: RM%.2f%n", amount);

        // Let customer select payment method
        Payment payment = selectPaymentMethod(booking.getBookingID(), amount);

        // Customer cancelled payment
        if (payment == null)
            return;

        // Process payment
        payment.processPayment();
        // Store payment record
        payments.add(payment);
        // Mark booking as paid
        booking.makePayment();
        // Increase payment ID
        nextPaymentID++;

        System.out.println("Payment successful!");
    }

    // Pay for a rental transaction
    public static void payRentalTransaction() {

        System.out.println("\n===== RENTAL TRANSACTION PAYMENT =====");
        System.out.print("Enter Transaction ID: ");
        String transactionID = input.nextLine();
        RentalTransaction transaction = transactionManager.findTransaction(transactionID);

        // Check whether transaction exists
        if (transaction == null) {
            MainUtils.displayRed("Transaction not found.");
            return;
        }

        // Check whether already paid
        if (transaction.isPaid()) {
            MainUtils.displayRed(
                    "This transaction has already been paid.");
            return;
        }

        // Get the best promotion
        Promotion bestPromotion = promotionManager.getBestPromotion(transaction);

        double discount = 0;

        if (bestPromotion != null) {
            discount = bestPromotion.calculateDiscount(transaction);
        }

        // Calculate final amount
        double amount = transaction.calculateFinalPayable(discount);

        System.out.printf("Final amount to pay: RM%.2f%n", amount);

        // Select payment method
        Payment payment = selectPaymentMethod(transaction.getTransactionID(), amount);

        // Customer cancelled payment
        if (payment == null)
            return;

        // Process payment
        payment.processPayment();

        // Store payment
        payments.add(payment);

        // Mark transaction as paid
        transaction.makePayment();

        transactionManager.saveTransactions();

        if (transaction.getBooking() != null) {
            transaction.getBooking().makePayment();
        }

        // Increase payment ID
        nextPaymentID++;

        System.out.println("Payment successful!");
    }
    
}

