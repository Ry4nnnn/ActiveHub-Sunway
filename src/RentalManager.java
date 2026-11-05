import java.util.ArrayList;

public class RentalManager {

    private ArrayList<RentalItem> rentalItems = new ArrayList<>();

    public RentalManager() {

        // Equipment
        rentalItems.add(new RentalItem("E001", "Standard Badminton Racket", "Equipment", 15.00));
        rentalItems.add(new RentalItem("E002", "Premium Badminton Racket", "Equipment", 30.00));
        rentalItems.add(new RentalItem("E003", "Basketball", "Equipment", 10.00));
        rentalItems.add(new RentalItem("E004", "Futsal Ball", "Equipment", 10.00));

        // Accessories
        rentalItems.add(new RentalItem("A001", "Sports Socks", "Accessory", 8.00));
        rentalItems.add(new RentalItem("A002", "Sports Towel", "Accessory", 10.00));
        
		// Facility Packages
		rentalItems.add(new RentalItem("P001", "Normal Package", "Facility Package", 14.00));
		rentalItems.add(new RentalItem("P002", "Premium Package", "Facility Package", 28.00));
    }

	// Display rental catalogue
	public void displayCatalogue() {

		System.out.println("\n============================== RENTAL CATALOGUE ==============================");
		System.out.printf("%-8s %-42s %-18s %s%n",
				"Code", "Item Name", "Category", "Price");

		System.out.println("------------------------------------------------------------------------------");

		int i = 0;

		while (i < rentalItems.size()) {

			RentalItem item = rentalItems.get(i);

			String itemName = item.getItemName();

			if (item.getItemCode().equals("P001")) {
				itemName = "Normal PKG (2 Drinks + 2 Snacks)";

			} else if (item.getItemCode().equals("P002")) {
				itemName = "Premium PKG (4 Drinks + 4 Snacks)";
			}

			String category = item.getCategory();

			if (category.equals("Facility Package")) {
				category = "Facility Package";
			}

			System.out.printf("%-8s %-42s %-18s RM%.2f%n",
					item.getItemCode(),
					itemName,
					category,
					item.getRentalPrice());

			i++;
		}

		System.out.println("------------------------------------------------------------------------------");
	}

	//returns the RentalItem object if found, else returns null
    public RentalItem findItem(String itemCode) {

        int i = 0;

        while (i < rentalItems.size()) {
            if (rentalItems.get(i).getItemCode().equalsIgnoreCase(itemCode)) {
                return rentalItems.get(i);
            }

            i++;
        }

        return null;
    }
}