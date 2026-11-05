public class RentalItem {

    private String itemCode;
    private String itemName;
    private String category;
    private double rentalPrice;

    // Constructor
    public RentalItem(String itemCode, String itemName,
                      String category, double rentalPrice) {

        this.itemCode = itemCode;
        this.itemName = itemName;
        this.category = category;
        this.rentalPrice = rentalPrice;
    }

    // Getters
    public String getItemCode() {
        return itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public String getCategory() {
        return category;
    }

    public double getRentalPrice() {
        return rentalPrice;
    }

    // Display rental item
    public void displayItem() {
        System.out.printf("%-8s %-25s %-15s RM%.2f%n",
                itemCode, itemName, category, rentalPrice);
    }
}