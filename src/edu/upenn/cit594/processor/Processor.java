package edu.upenn.cit594.processor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
		
		Map<String, Integer> results = new HashMap<>();
		
		if(results.containsKey("total")) {
			return results.get("total");
		}
		else {
			int total = 0;
			for(PopulationObject p : this.populations) {
				total += Integer.parseInt(p.getPopulationString());
			}
			results.put("total", total);
			return total;
		}
	}

	//helper method to maintain population format in hashmap form for faster processing
	private HashMap<Integer, Double> populationInHashMapForm() {
		HashMap<Integer, Double> popHashMap = new HashMap<>();
		for (PopulationObject p : this.populations) {
			popHashMap.put(Integer.parseInt(p.getZipCode()), Double.parseDouble(p.getPopulationString()));
		}
		return popHashMap;
	}
	
	//helper method to get total number of tickets per zipcode in hashmap form
		private HashMap<String, Integer> totalTicketsPerZipCode() {
			HashMap<String, Integer> ticketsHashMap = new HashMap<>();
			for (ParkingViolationObject p : this.parkingViolations) {
				if(p.getZipcode() == null) {
					continue;
				}
				else {
					if(ticketsHashMap.containsKey(p.getZipcode())) {
						int count = ticketsHashMap.get(p.getZipcode()) + 1;
						ticketsHashMap.put(p.getZipcode(), count);
					}else {
						ticketsHashMap.put(p.getZipcode(), 1);
					}
				}
			}
			return ticketsHashMap;
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
		
		Map<String, TreeMap<Integer,String>> results = new HashMap<>();
		if(results.containsKey("answer")) {
			return results.get("answer");
		}
			else {
			TreeMap<Integer, String> sortedTotalFinesPerCapitaByZipCode = new TreeMap<>();
			for (Integer zipCode : totalAggregateFineByZipCode().keySet()) {
				if (populationInHashMapForm().containsKey(zipCode)) {
					double zipCodePopulation = populationInHashMapForm().get(zipCode);
					double fineInZipCode = totalAggregateFineByZipCode().get(zipCode);
					double zipCodeFinePerCapita = fineInZipCode/zipCodePopulation;
					sortedTotalFinesPerCapitaByZipCode.put(zipCode, truncate(zipCodeFinePerCapita, 4));
				}
			}
			results.put("answer", sortedTotalFinesPerCapitaByZipCode);
			return sortedTotalFinesPerCapitaByZipCode;
		}
	}
	
	//step 3 & 4 -- calculate average market value or total livable area by number of residences
	public int getAverage(AverageComparator comparator, int zipcode) {
		
		return Integer.parseInt(truncate(comparator.getAverage(properties, zipcode),0));
	}
	
	//step 5 - total residential value per capita
	public int getMarketValuePerCapita(String zipCode) {
		
		Map<String, Integer> results = new HashMap<>();
		if(results.containsKey(zipCode)) {
			return results.get(zipCode);
		}
		else {
			
			double totalMarketValue = 0;
			String regex = "^[0-9]{5}$";
			//if it does not meet the zip-code criteria
			if (zipCode == null || zipCode.isEmpty() || !zipCode.matches(regex)) {
				return 0;
			}
			//if the zip-code is not in the population file containing all zipcodes
			if (!populationInHashMapForm().containsKey(Integer.parseInt(zipCode))) {
				return 0;
			}
			int zipCodeValue = Integer.parseInt(zipCode);
			double populationOfZipCode = populationInHashMapForm().get(zipCodeValue);
			System.out.println("Population of zipcode: " + populationOfZipCode);
			for (Property p : this.properties) {
				//if zipcode value matches, add to totalMarketValue
				if (p.getZipCode().equals(zipCode)) {
					System.out.println(zipCode);
					totalMarketValue += Double.parseDouble(p.getMarketValue());
				}
			}
			System.out.println("Total market value: "  + totalMarketValue);
			
			results.put(zipCode, Integer.parseInt(truncate(totalMarketValue/populationOfZipCode, 0)));
			return Integer.parseInt(truncate(totalMarketValue/populationOfZipCode, 0));
		}
	}
	
	//6 Display the zip code with the lowest ticket number per capita within the user budget
	public HashMap<String, Double> safeMethod(double budget) {
		
		//get number of tickets per zipcode
		HashMap<String, Integer> ticketsHashMap = totalTicketsPerZipCode();
		HashMap<Integer, Double> popHashMap = populationInHashMapForm();
		HashMap<String, Double> safeZipCodeTreeMap = new HashMap<>();
		
		//loop through all the zipcodes in the population file
		for (Map.Entry<Integer, Double> entry : popHashMap.entrySet()) {
		    String zipCode = Integer.toString(entry.getKey());
			Double popForZipCode = entry.getValue();
			
			//check to see if that zipcode has a ticket number in the ticketsHashmap
			int ticketNumber;
			//if there was no ticket for that zipcode, set the ticket number to 0
			if(!ticketsHashMap.containsKey(zipCode)) {
				ticketNumber = 0;
			}
			else {
				ticketNumber = ticketsHashMap.get(zipCode);
			}
			int marketValue = getAverage(new MarketValueComparator(), Integer.parseInt(zipCode));
			Double ticketPerCapita = ticketNumber / popForZipCode;
			if(marketValue > budget) {
				continue;
			}else {
				safeZipCodeTreeMap.put(zipCode, ticketPerCapita);
			}
		}
		return safeZipCodeTreeMap;
	}



//	used for testing -- delete after
	public static void main(String[] args) {
		//must import properties file
//		CSVPropertyReader cv = new CSVPropertyReader("properties.csv");
//		Reader r = new JSONFileReader("parking.json");
//		PopulationFileReader pr = new PopulationFileReader("population.txt");
//		Processor p = new Processor(r, pr.getPopulationObjects(), cv.getPropertyObjects());
//		for (Integer zip : p.totalFinePerCapita().keySet()) {
//			System.out.println(zip + " " + p.totalFinePerCapita().get(zip));
//		}
	}
}
