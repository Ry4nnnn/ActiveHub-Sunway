public class PaymentEWallet extends Payment {

    // Constructor
    public PaymentEWallet(String paymentID, String referenceID, double amount) {
        super(paymentID, referenceID, amount);
    }

    // Process e-wallet payment
    @Override
    public void processPayment() {
        System.out.println("E-wallet payment processed successfully.");
    }

    // Get payment method
    @Override
    public String getPaymentMethod() {
        return "E-Wallet";
    }
}