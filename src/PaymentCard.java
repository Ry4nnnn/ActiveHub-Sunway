public class PaymentCard extends Payment {

    // Constructor
    public PaymentCard(String paymentID, String referenceID, double amount) {
        super(paymentID, referenceID, amount);
    }

    // Process card payment
    @Override
    public void processPayment() {
        System.out.println("Credit/Debit card payment processed successfully.");
    }

    // Get payment method
    @Override
    public String getPaymentMethod() {
        return "Credit/Debit Card";
    }
}

