package edu.upenn.cit594.processor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import edu.upenn.cit594.data.ParkingViolationObject;
import edu.upenn.cit594.data.PopulationObject;
import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.datamanagement.CSVPropertyReader;
import edu.upenn.cit594.datamanagement.JSONFileReader;
import edu.upenn.cit594.datamanagement.PopulationFileReader;
import edu.upenn.cit594.datamanagement.Reader;

public class Processor {

	protected Reader reader;
	protected ArrayList<ParkingViolationObject> parkingViolations;
	protected ArrayList<PopulationObject> populations;
	protected ArrayList<Property> properties;
	
	public Processor(Reader reader, ArrayList<PopulationObject> pop, ArrayList<Property> properties) {
		this.reader = reader;
		this.parkingViolations = reader.getParkingViolationObjects();
		this.populations = pop;
		this.properties = properties;
	}

	//step 1 - calculate total population of all zipcodes
	public int calculatePopulation() {
		int total = 0;
		for(PopulationObject p : this.populations) {
			total += Integer.parseInt(p.getPopulationString());
		}
		return total;
	}

	//helper method to maintain population format in hashmap form for faster processing
	private HashMap<Integer, Double> populationInHashMapForm() {
		HashMap<Integer, Double> popHashMap = new HashMap<>();
		for (PopulationObject p : this.populations) {
			popHashMap.put(Integer.parseInt(p.getZipCode()), Double.parseDouble(p.getPopulationString()));
		}
		return popHashMap;
	}

	//helper method for aggregating populations by zipcode: total aggregate amount of fines for that zipcode
	//does not read in any null values, or if there is no fine
	private HashMap<Integer, Double> totalAggregateFineByZipCode() {
		HashMap<Integer, Double> hm = new HashMap<>();
		for (ParkingViolationObject p : this.parkingViolations) {
			//has to equal "PA" or have a value for zip code (non-null or a blank value)
			if (p.getState().equals("PA") && p.getZipcode() != null && !p.getZipcode().equals("")) {
				int zipCode = Integer.parseInt(p.getZipcode());
				double fine = Double.parseDouble(p.getFine());
				if (hm.containsKey(zipCode)) {
					hm.put(zipCode, (hm.get(zipCode) + fine));
				} else {
					hm.put(zipCode, fine);
				}
			}
		}
		return hm;
	}

	//helper method for truncation of values to the required decimal places, HAS to be in STRING Format, and converted
	//for later use
	private static String truncate(Double value, int places) {
		return new BigDecimal(value).setScale(places, RoundingMode.DOWN).toString();
	}


	//step 2 -- calculate total fine per capita
	//use of tree map to sort key of zipcodes
	public TreeMap<Integer, String> totalFinePerCapita() {
		TreeMap<Integer, String> sortedTotalFinesPerCapitaByZipCode = new TreeMap<>();
		for (Integer zipCode : totalAggregateFineByZipCode().keySet()) {
			if (populationInHashMapForm().containsKey(zipCode)) {
				double zipCodePopulation = populationInHashMapForm().get(zipCode);
				double fineInZipCode = totalAggregateFineByZipCode().get(zipCode);
				double zipCodeFinePerCapita = fineInZipCode/zipCodePopulation;
				sortedTotalFinesPerCapitaByZipCode.put(zipCode, truncate(zipCodeFinePerCapita, 4));
			}
		}
		return sortedTotalFinesPerCapitaByZipCode;
	}
	
	//step 3 & 4 -- calculate average market value or total livable area by number of residences
	public int getAverage(AverageComparator comparator, int zipcode) {
		
		return Integer.parseInt(Double.toString(comparator.getAverage(properties, zipcode)));
	}
	
	//step 5 - total residential value per capita
	public double getMarketValuePerCapita(String zipCode) {
		double totalMarketValue = 0;
		String regex = "^[0-9]{5}$";
		//if it does not meet the zip-code criteria
		if (zipCode == null || zipCode.isEmpty() || !zipCode.matches(regex)) {
			return 0;
		}
		//if the zip-code is not in the population file containing all zipcodes
		Double populationOfZipCode;
		if (!populationInHashMapForm().containsKey(Integer.parseInt(zipCode))) {
			return 0;
		} else {
			int zipCodeValue = Integer.parseInt(zipCode);
			populationOfZipCode = populationInHashMapForm().get(zipCodeValue);
		}
		for (Property p : this.properties) {
			//if zipcode value matches, add to totalMarketValue
			if (p.getZipCode().equals(zipCode) && populationInHashMapForm().containsKey(Integer.parseInt(zipCode))) {
				totalMarketValue += Double.parseDouble(p.getMarketValue());
			}
		}

		return Double.parseDouble(truncate(totalMarketValue/populationOfZipCode, 0));
	}



//	used for testing -- delete after
	public static void main(String[] args) {
//		//must import properties file
//		CSVPropertyReader cv = new CSVPropertyReader("properties.csv");
//		Reader r = new JSONFileReader("parking.json");
//		PopulationFileReader pr = new PopulationFileReader("population.txt");
//		Processor p = new Processor(r, pr.getPopulationObjects(), cv.getPropertyObjects());
//		for (Integer zip : p.totalFinePerCapita().keySet()) {
//			System.out.println(zip + " " + p.totalFinePerCapita().get(zip));
//		}
	}
}
