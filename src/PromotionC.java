public class PromotionC extends Promotion {

    // Constructor
    public PromotionC() {
        super("C", "RM20 Off for 3 Equipment Items");
    }

    // Calculate Promotion C discount
    @Override
    public double calculateDiscount(
            RentalTransaction transaction) {

        // Must have a facility booking
        if (transaction.getBooking() == null) {
            return 0;
        }

        // Must have at least 3 equipment items
        if (transaction.getEquipmentQuantity() >= 3) {
            return 20.00;
        }

        return 0;
    }
}