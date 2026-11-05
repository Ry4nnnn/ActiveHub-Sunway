import java.time.LocalTime;

public class PromotionA extends Promotion {

    // Constructor
    public PromotionA() {
        super("A", "15% Off Facility Charges");
    }

    // Calculate Promotion A discount
    @Override
    public double calculateDiscount(
            RentalTransaction transaction) {

        Booking booking = transaction.getBooking();

        // Walk-in customers are not eligible
        if (booking == null) {
            return 0;
        }

        boolean isWeekday =
                booking.getBookingDate().getDayOfWeek().getValue() >= 1
                && booking.getBookingDate().getDayOfWeek().getValue() <= 5;

        LocalTime bookingTime = booking.getBookingTime();

        boolean isValidTime =
                !bookingTime.isBefore(LocalTime.of(9, 0))
                && bookingTime.isBefore(LocalTime.of(16, 0));

        if (isWeekday && isValidTime) {
            return booking.calculateBookingPrice() * 0.15;
        }

        return 0;
    }
}