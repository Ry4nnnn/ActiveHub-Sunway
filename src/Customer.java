public class Customer {

    private String name;
    private String contactNumber;

    // Constructor
    public Customer(String name, String contactNumber) {
        this.name = name;
        this.contactNumber = contactNumber;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    // Display customer information
    public void displayCustomer() {
        System.out.println("Customer Name: " + name);
        System.out.println("Contact Number: " + contactNumber);
    }
}
