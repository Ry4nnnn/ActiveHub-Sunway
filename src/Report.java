public class Report {

    public static void displayDailyReport(
            BookingManager bookingManager,
            TransactionManager transactionManager) {

        System.out.println("\n========== DAILY REPORT ==========");

        // Booking information
        int totalBookings = bookingManager.getBookingCount();

        int paidBookings = bookingManager.getPaidBookingCount();

        int unpaidBookings = totalBookings - paidBookings;

        double bookingRevenue = bookingManager.calculateTotalBookingRevenue();

        // Rental transaction information
        int totalTransactions = transactionManager.getTransactionCount();

        int paidTransactions = transactionManager.getPaidTransactionCount();

        int unpaidTransactions = totalTransactions - paidTransactions;

        double rentalRevenue = transactionManager.calculateTotalRevenue();


        // Grand total
        double totalRevenue =
                bookingRevenue + rentalRevenue;


        System.out.println("\n----- FACILITY BOOKINGS -----");
        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Paid Bookings: " + paidBookings);
        System.out.println("Unpaid Bookings: " + unpaidBookings);
        System.out.printf("Facility Revenue: RM%.2f%n", bookingRevenue);

        System.out.println("\n----- RENTAL TRANSACTIONS -----");
        System.out.println("Total Transactions: " + totalTransactions);
        System.out.println("Paid Transactions: " + paidTransactions);
        System.out.println("Unpaid Transactions: " + unpaidTransactions);
        System.out.printf("Rental Revenue: RM%.2f%n", rentalRevenue);

        System.out.println("\n========== TOTAL ==========");
        System.out.printf("Total Daily Revenue: RM%.2f%n", totalRevenue);

        System.out.println("============================");
    }
}