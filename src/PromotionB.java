public class PromotionB extends Promotion {

    // Constructor
    public PromotionB() {
        super("B", "RM5 Off Per Participant");
    }

    // Calculate Promotion B discount
    @Override
    public double calculateDiscount(
            RentalTransaction transaction) {

        Booking booking = transaction.getBooking();

        // Walk-in customers are not eligible
        if (booking == null) {
            return 0;
        }

        int participants = booking.getParticipants();

        // Minimum 8 participants required
        if (participants < 8) {
            return 0;
        }

        double discount = participants * 5.00;

        // Maximum discount is RM60
        if (discount > 60.00) {
            discount = 60.00;
        }

        return discount;
    }
}