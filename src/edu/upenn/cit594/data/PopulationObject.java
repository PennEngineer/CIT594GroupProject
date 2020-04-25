package edu.upenn.cit594.data;

/**
 * This class represents a Population object and has several instance variables pertaining to
 * how the object is represented.
 */
public class PopulationObject {
	
	private String zipCode;
	private String populationString;

	/**
	 * Constructor for the PopulationObject.
	 * @param zipCode - the zipcode area
	 * @param populationString - the population of people
	 */
	public PopulationObject(String zipCode, String populationString) {
		this.zipCode = zipCode;
		this.populationString = populationString;
	}

	//getters and setters for the PopulationObject
	public String getZipCode() {
		return zipCode;
	}

	public String getPopulationString() {
		return populationString;
	}
}
