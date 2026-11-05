public abstract class Promotion {

    private String promotionCode;
    private String promotionName;

    // Constructor
    public Promotion(String promotionCode, String promotionName) {
        this.promotionCode = promotionCode;
        this.promotionName = promotionName;
    }

    // Getters
    public String getPromotionCode() {
        return promotionCode;
    }

    public String getPromotionName() {
        return promotionName;
    }

    // Each promotion must calculate its own discount
	//PromotionA object -> PromotionA.calculateDiscount()
	//PromotionB object -> PromotionB.calculateDiscount()
	//PromotionC object -> PromotionC.calculateDiscount()
    public abstract double calculateDiscount(
            RentalTransaction transaction);
}