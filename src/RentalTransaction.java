package ActiveHubSportCentre.src;

import java.util.LinkedHashMap;
import java.util.Map;

public class RentalTransaction {

    private String transactionID;
    private Customer customer;

    private Map<RentalItem, Integer> rentalItems;

    // Constructor
    public RentalTransaction(String transactionID, Customer customer) {

        this.transactionID = transactionID;
        this.customer = customer;

        rentalItems = new LinkedHashMap<>();
    }

    // Getters
    public String getTransactionID() {
        return transactionID;
    }

    public Customer getCustomer() {
        return customer;
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

    // Calculate 10% service charge
    public double calculateServiceCharge() {

        return calculateSubtotal() * 0.10;
    }

    // Calculate total
    public double calculateTotal() {

        return calculateSubtotal() + calculateServiceCharge();
    }

    // Display transaction
    public void displayTransaction() {

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

        System.out.printf("Subtotal: RM%.2f%n", calculateSubtotal());

        System.out.printf("Service Charge (10%%): RM%.2f%n",
                calculateServiceCharge());

        System.out.printf("Final Total: RM%.2f%n", calculateTotal());

        System.out.println("=================================");
    }
}