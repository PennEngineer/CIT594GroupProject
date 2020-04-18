package edu.upenn.cit594.data;

public class Property {

    private String marketValue;
    private String totalLivableArea;
    private String zipCode;

    public Property(String marketValue, String totalLivableArea, String zipCode) {
        this.marketValue = marketValue;
        this.totalLivableArea = totalLivableArea;
        this.zipCode = zipCode;
    }

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
