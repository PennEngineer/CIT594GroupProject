package edu.upenn.cit594.data;

/**
 * This class represents the Property object from a csv file representing property information
 * of the area.
 */
public class Property {

    private String marketValue;
    private String totalLivableArea;
    private String zipCode;

    /**
     * Constructor for the Property object.
     * @param marketValue - value of the property in USD
     * @param totalLivableArea - the size of the livable area in sq ft
     * @param zipCode - the zipcode of the property
     */
    public Property(String marketValue, String totalLivableArea, String zipCode) {
        this.marketValue = marketValue;
        this.totalLivableArea = totalLivableArea;
        this.zipCode = zipCode;
    }

    //getters and setters for the Property object.
    public String getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(String marketValue) {
        this.marketValue = marketValue;
    }

    public String getTotalLivableArea() {
        return totalLivableArea;
    }

    public void setTotalLivableArea(String totalLivableArea) {
        this.totalLivableArea = totalLivableArea;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
