public abstract class Payment {

    private String paymentID;
    private String referenceID;
    private double amount;

    // Constructor
    public Payment(String paymentID,
                   String referenceID,
                   double amount) {

        this.paymentID = paymentID;
        this.referenceID = referenceID;
        this.amount = amount;
    }

    // Getters
    public String getPaymentID() {
        return paymentID;
    }

    public String getReferenceID() {
        return referenceID;
    }

    public double getAmount() {
        return amount;
    }

    // Each payment method processes payment differently
    public abstract void processPayment();

    // Each child provides its payment method name
    public abstract String getPaymentMethod();
}

