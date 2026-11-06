public class PaymentCash extends Payment {

    // Constructor
    public PaymentCash(String paymentID, String referenceID, double amount) {
        super(paymentID, referenceID, amount);
    }

    // Process cash payment
    @Override
    public void processPayment() {
        System.out.println("Cash payment processed successfully.");
    }

    // Get payment method
    @Override
    public String getPaymentMethod() {
        return "Cash";
    }
}

