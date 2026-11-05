package ActiveHubSportCentre.src;

public class Facility {

    private String facilityCode;
    private String facilityName;
    private double rentalPrice;

    // Constructor
    public Facility(String facilityCode, String facilityName, double rentalPrice) {
        this.facilityCode = facilityCode;
        this.facilityName = facilityName;
        this.rentalPrice = rentalPrice;
    }

    // Getters
    public String getFacilityCode() {
        return facilityCode;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public double getRentalPrice() {
        return rentalPrice;
    }

    // Display facility information
    public void displayFacility() {
        System.out.println("Facility Code: " + facilityCode);
        System.out.println("Facility Name: " + facilityName);
        System.out.printf("Rental Price: RM%.2f%n", rentalPrice);
    }
}