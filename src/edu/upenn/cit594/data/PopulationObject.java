package edu.upenn.cit594.data;

public class PopulationObject {
	
	private String zipCode;
	private String populationString;
	
	public PopulationObject(String zipCode, String populationString) {
		this.zipCode = zipCode;
		this.populationString = populationString;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getPopulationString() {
		return populationString;
	}
}
