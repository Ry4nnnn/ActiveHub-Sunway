import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class RentalTransaction {

    private String transactionID;
    private Customer customer;
    private Booking booking;
    private boolean paid;

    private Map<RentalItem, Integer> rentalItems;

    // Constructor
    public RentalTransaction(String transactionID, Customer customer, Booking booking) {

        this.transactionID = transactionID;
        this.customer = customer;
        this.booking = booking;
        this.paid = false;

        rentalItems = new LinkedHashMap<>();
    }

    // Getters
    public String getTransactionID() {
        return transactionID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Booking getBooking() {
        return booking;
    }

    public boolean isPaid() {
        return paid;
    }

    public void makePayment() {
        paid = true;
    }
    
    public Map<RentalItem, Integer> getRentalItems() {
        return rentalItems;
    }

    // Calculate facility booking charge
    public double calculateFacilityCharge() {

        if (booking == null) {
            return 0.00;
        }

        return booking.calculateBookingPrice();
    }

    // Add rental item
    public void addItem(RentalItem item, int quantity) {

        if (quantity <= 0) {
            System.out.println("Invalid quantity!");
            return;
        }
        rentalItems.put(
                item,
                rentalItems.getOrDefault(item, 0) + quantity
        );
        System.out.println("Item added successfully!");
    }

    // Check whether an item exists in the transaction
    //returns true if item exist in rentalItem
    public boolean containsItem(RentalItem item) {
        return rentalItems.containsKey(item);
    }

    // Update existing rental item quantity
    public void updateItemQuantity(RentalItem item, int quantity) {

        if (!rentalItems.containsKey(item)) {
            System.out.println("Item not found in transaction!");
            return;
        }
        if (quantity < 0) {
            System.out.println("Invalid quantity!");
            return;
        }
        if (quantity == 0) {
            rentalItems.remove(item);
            System.out.println("Item removed successfully!");
        } else {
            rentalItems.put(item, quantity);
            System.out.println("Quantity updated successfully!");
        }
    }

    // Calculate subtotal
    public double calculateSubtotal() {

        double subtotal = 0;

        for (Map.Entry<RentalItem, Integer> entry : rentalItems.entrySet()) {

            double price = entry.getKey().getRentalPrice();
            int quantity = entry.getValue();

            subtotal += price * quantity;
        }

        return subtotal;
    }

    // Calculate subtotal + facility charge
    public double calculateCombinedSubtotal() {
        return calculateFacilityCharge() + calculateSubtotal();
    }

    // Calculate 10% service charge
    public double calculateServiceCharge() {
        return calculateCombinedSubtotal() * 0.10;
    }

    // Calculate final payable amount
    public double calculateFinalPayable(double discount) {
        return calculateCombinedSubtotal() - discount + calculateServiceCharge();
    }

    // Display transaction
    public void displayTransaction(PromotionManager promotionManager) {

        Promotion bestPromotion = promotionManager.getBestPromotion(this);
        double discount = 0;

        if (bestPromotion != null) {
            discount = bestPromotion.calculateDiscount(this);
        }

        System.out.println("\n========== RENTAL BILL ==========");
        System.out.println("Transaction ID: " + transactionID);
        System.out.println("Customer: " + customer.getName());
        System.out.println("---------------------------------");

        for (Map.Entry<RentalItem, Integer> entry : rentalItems.entrySet()) {

            RentalItem item = entry.getKey();

            int quantity = entry.getValue();

            double total = item.getRentalPrice() * quantity;

            System.out.printf("%-20s x%-3d RM%.2f%n",
                    item.getItemName(), quantity, total);
        }

        System.out.println("---------------------------------");
        System.out.printf("Rental Items Subtotal: RM%.2f%n", calculateSubtotal());
        System.out.printf("Facility Charge: RM%.2f%n", calculateFacilityCharge());
        System.out.printf("Combined Subtotal: RM%.2f%n", calculateCombinedSubtotal());

        // Display eligible promotions
        promotionManager.displayEligiblePromotions(this);

        if (bestPromotion != null) {
            System.out.println("Selected Promotion: "
                + bestPromotion.getPromotionCode()
                + " - "
                + bestPromotion.getPromotionName());
            System.out.printf("Discount: RM%.2f%n", discount);

        } else {
            System.out.println("Selected Promotion: None");
            System.out.printf("Discount: RM%.2f%n", 0.00);
        }

        System.out.printf("Service Charge (10%%): RM%.2f%n",
                calculateServiceCharge());
        System.out.printf("Final Payable: RM%.2f%n",
                calculateFinalPayable(discount));
        System.out.println("=================================");
    }

    // Get total quantity of equipment items
    public int getEquipmentQuantity() {

        int totalEquipment = 0;

        ArrayList<RentalItem> items = new ArrayList<>(rentalItems.keySet());

        int i = 0;
        while (i < items.size()) {

            RentalItem item = items.get(i);

            if (item.getCategory().equalsIgnoreCase("Equipment"))
                totalEquipment += rentalItems.get(item);
            i++;
        }
        return totalEquipment;
    }

}


