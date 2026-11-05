import java.util.ArrayList;

public class PromotionManager {

	//creates a list to store Promotion objects (A,B,C) child
    private ArrayList<Promotion> promotions = new ArrayList<>();

    // Constructor
    public PromotionManager() {

        promotions.add(new PromotionA());
        promotions.add(new PromotionB());
        promotions.add(new PromotionC());
    }

	// Get the promotion with the greatest discount
	// Return the best Promotion Child Object A B C
	public Promotion getBestPromotion(RentalTransaction transaction) {

		Promotion bestPromotion = null;
		double bestDiscount = 0;

		int i = 0;
		while (i < promotions.size()) {

			Promotion promotion = promotions.get(i);
			double discount = promotion.calculateDiscount(transaction);

			if (discount > bestDiscount) {
				bestDiscount = discount;
				bestPromotion = promotion;
			}
			i++;
		}
		return bestPromotion;
	}

	// Display all eligible promotions
	/*
	===== ELIGIBLE PROMOTIONS =====
	A - 15% Off Facility Charges: RM12.00 discount
	B - RM5 Off Per Participant: RM40.00 discount
	C - RM20 Off for 3 Equipment Items: RM20.00 discount 
	===== ELIGIBLE PROMOTIONS =====
	No eligible promotions.
	*/
	public void displayEligiblePromotions(RentalTransaction transaction) {

		System.out.println("\n===== ELIGIBLE PROMOTIONS =====");
		boolean gotPromo = false;

		int i = 0;
		while (i < promotions.size()) {
			Promotion promotion = promotions.get(i);
			double discount = promotion.calculateDiscount(transaction);

			if (discount > 0) {
				System.out.printf("%s - %s: RM%.2f discount%n",
					promotion.getPromotionCode(), promotion.getPromotionName(), discount);
				gotPromo = true;
			}
			i++;
		}

		if (!gotPromo) {
			System.out.println("No eligible promotions.");
		}
	}

}
