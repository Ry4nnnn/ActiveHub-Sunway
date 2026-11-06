import java.util.ArrayList;
import java.util.Map;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class TransactionManager {

    private ArrayList<RentalTransaction> transactions;

    // Constructor
    public TransactionManager() {
        transactions = new ArrayList<>();
    }

    // Add transaction
    public void addTransaction(RentalTransaction transaction) {
        transactions.add(transaction);
		saveTransactions();
    }

    // Find transaction using Transaction ID
    public RentalTransaction findTransaction(String transactionID) {

        int i = 0;

        while (i < transactions.size()) {

            RentalTransaction transaction = transactions.get(i);

            if (transaction.getTransactionID().equalsIgnoreCase(transactionID)) {
                return transaction;
            }
            i++;
        }

        return null;
    }

	// Check whether a booking already has a transaction
	public boolean hasTransactionForBooking(String bookingID) {

		int i = 0;

		while (i < transactions.size()) {

			RentalTransaction transaction = transactions.get(i);

			Booking booking = transaction.getBooking();

			if (booking != null
					&& booking.getBookingID().equalsIgnoreCase(bookingID)) {

				return true;
			}

			i++;
		}

		return false;
	}

    // Get transaction count
    public int getTransactionCount() {
        return transactions.size();
    }

	// Save transactions to Transactions.txt
	public void saveTransactions() {

		try {
			PrintWriter writer = new PrintWriter(new FileWriter("../data/transactions.txt"));

			int i = 0;

			while (i < transactions.size()) {

				RentalTransaction transaction = transactions.get(i);

				String transactionID = transaction.getTransactionID();
				String name = transaction.getCustomer().getName();
				String contact = transaction.getCustomer().getContactNumber();
				String bookingID = "NONE";

				if (transaction.getBooking() != null) {
					bookingID = transaction.getBooking().getBookingID();
				}

				String paid = String.valueOf(transaction.isPaid());

				// Build rental item list
				String items = "";

				for (Map.Entry<RentalItem, Integer> entry : transaction.getRentalItems().entrySet()) {

					RentalItem item = entry.getKey();
					int quantity = entry.getValue();

					if (!items.isEmpty())
						items += ",";

					items += item.getItemCode()
							+ ":" + quantity;
				}

				writer.println(transactionID + "|" + name + "|"+ contact + "|"
					+ bookingID + "|" + paid + "|" + items);
				i++;
			}
			writer.close();

		} catch (IOException e) {
			System.out.println("Error saving transactions."
			);
		}
	}

	// Load transactions from Transactions.txt
	public void loadTransactions(BookingManager bookingManager, RentalManager rentalManager) {

		File file = new File("../data/transactions.txt");

		if (!file.exists()) {
			return;
		}

		try {

			BufferedReader reader =
					new BufferedReader(new FileReader(file));

			String line;

			while ((line = reader.readLine()) != null) {

				String[] data = line.split("\\|", -1);

				if (data.length == 6) {

					String transactionID = data[0];
					String name = data[1];
					String contact = data[2];
					String bookingID = data[3];
					boolean paid =
							Boolean.parseBoolean(data[4]);

					String items = data[5];

					// Create customer
					Customer customer = new Customer(name, contact);

					// Find linked booking
					Booking booking = null;

					if (!bookingID.equalsIgnoreCase("NONE")) {
						booking = bookingManager.findBookingByID(bookingID);
					}

					// Create transaction
					RentalTransaction transaction = new RentalTransaction(
								transactionID, customer, booking);

					// Restore rental items
					if (!items.isEmpty()) {

						String[] itemList = items.split(",");

						int i = 0;

						while (i < itemList.length) {

							String[] itemData =
									itemList[i].split(":");

							if (itemData.length == 2) {

								String itemCode = itemData[0];

								int quantity =
										Integer.parseInt(itemData[1]);

								RentalItem item =
										rentalManager.findItem(itemCode);

								if (item != null) {

									transaction.addItem(
											item,
											quantity
									);
								}
							}

							i++;
						}
					}

					// Restore payment status
					if (paid) {
						transaction.makePayment();
					}

					transactions.add(transaction);
				}
			}

			reader.close();

			System.out.println("Previous transactions loaded.");

		} catch (IOException | RuntimeException e) {
			System.out.println("Error loading transactions: " + e.getMessage());
		}
	}

	public int getPaidTransactionCount() {

		int count = 0;
		int i = 0;

		while (i < transactions.size()) {
			if (transactions.get(i).isPaid())
				count++;
			i++;
		}
		return count;
	}

	public double calculateTotalRevenue() {

		double totalRevenue = 0;
		int i = 0;

		while (i < transactions.size()) {
			RentalTransaction transaction = transactions.get(i);

			if (transaction.isPaid())
				totalRevenue += transaction.calculateSubtotal();
			i++;
		}
		return totalRevenue;
	}

}