package edu.upenn.cit594.processor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import edu.upenn.cit594.data.ParkingViolationObject;
import edu.upenn.cit594.data.PopulationObject;
import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.datamanagement.Reader;

/**
 * This is the processor class which makes all the calculations for all the data that is read in from the
 * data management package. It has all the methods necessary to perform calculations and store them in
 * easily readable data structures. It also makes use of memoization features for more efficient processing
 * of information.
 */
public class Processor {

	protected Reader reader;
	protected ArrayList<ParkingViolationObject> parkingViolations;
	protected ArrayList<PopulationObject> populations;
	protected ArrayList<Property> properties;
	private int populationResults = -1;
	private TreeMap<Integer, String> totalFinePerCapitaResults = new TreeMap<>();
	private HashMap<Integer, Integer> averageMarketValueResults = new HashMap<>();
	private HashMap<Integer, Integer> averageTotalLivableAreaResults = new HashMap<>();
	private HashMap<String, Integer> marketValuePerCapitaResults = new HashMap<>();
	private HashMap<Double, HashMap<String, Double>> safeMethodResults = new HashMap<>();

	/**
	 * Constructor for the processor class that is passed into the UI package for displaying of information.
	 * @param reader - the specific reader for the file.
	 * @param pop - an array list of populations that was read in.
	 * @param properties - an array list of properties that was read in.
	 */
	public Processor(Reader reader, ArrayList<PopulationObject> pop, ArrayList<Property> properties) {
		this.reader = reader;
		this.parkingViolations = reader.getParkingViolationObjects();
		this.populations = pop;
		this.properties = properties;
	}

	/**
	 * Step 1
	 * This method calculates the total population from all the zip codes provided in population.txt.
	 * It sums up all the populations from all the zip codes. It also uses a int variable for memoization to efficiently
	 * print the number again if the value is called instead of processing the information again.
	 * @return - the total population from all the zip codes.
	 */
	public int calculatePopulation() {
		
		//Memoization technique
		if (populationResults != -1) {
			return populationResults;
		} 
		else {
			int total = 0;
			for (PopulationObject p : populations) {
				total += Integer.parseInt(p.getPopulationString());
			}
			populationResults = total;
			return total;
		}
	}

	/**
	 * This is a private method for storing the population in a hashmap format for more efficient reading and processing.
	 * @return - a hashmap of zip code keys and population values.
	 */
	private HashMap<Integer, Double> populationInHashMapForm() {
		HashMap<Integer, Double> popHashMap = new HashMap<>();
		for (PopulationObject p : this.populations) {
			popHashMap.put(Integer.parseInt(p.getZipCode()), Double.parseDouble(p.getPopulationString()));
		}
		return popHashMap;
	}

	/**
	 * Private helper method to get the total # of tickets per zip code in hashmap form.
	 * @return - hashmap for total tickets per zip code.
	 */
	private HashMap<String, Integer> totalTicketsPerZipCode() {
		HashMap<String, Integer> totalTicketsPerZIPCodeHashMap = new HashMap<>();
		for (ParkingViolationObject p : this.parkingViolations) {
			if(p.getZipcode() == null) {
				continue;
			}
			else {
				if(totalTicketsPerZIPCodeHashMap.containsKey(p.getZipcode())) {
					int count = totalTicketsPerZIPCodeHashMap.get(p.getZipcode()) + 1;
					totalTicketsPerZIPCodeHashMap.put(p.getZipcode(), count);
				} else {
					totalTicketsPerZIPCodeHashMap.put(p.getZipcode(), 1);
				}
			}
		}
		return totalTicketsPerZIPCodeHashMap;
	}


	/**
	 * Helper method for aggregating populations by zip code: total aggregate amount of fines for that zip code
	 * does not read in any null values, or if there is no fine.
	 * @return hash map of Integer, Double which has the total aggregate fine for that zip code.
	 */
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

	/**
	 * Helper method for truncation of values as oppose to rounding.
	 * @param value - value to be truncated
	 * @param places - how many places of truncation.
	 * @return - a String form of the truncated value.
	 */
	private String truncate(Double value, int places) {
		return new BigDecimal(value).setScale(places, RoundingMode.DOWN).toString();
	}


	/**
	 * Step 2
	 * This method uses a tree map data structure. It also uses memoization to store that information.
	 * It calculates the total fine per capita of all the zip codes. It then stores that information in sorted format by zipcode.
	 * @return -  a tree map of zip code keys and fine per capita values.
	 */
	public TreeMap<Integer, String> totalFinePerCapita() {

		if (!totalFinePerCapitaResults.isEmpty()) {
			return totalFinePerCapitaResults;
		}
		TreeMap<Integer, String> sortedTotalFinesPerCapitaByZipCode = new TreeMap<>();
		for (Integer zipCode : totalAggregateFineByZipCode().keySet()) {
			if (populationInHashMapForm().containsKey(zipCode)) {
				double zipCodePopulation = populationInHashMapForm().get(zipCode);
				double fineInZipCode = totalAggregateFineByZipCode().get(zipCode);
				double zipCodeFinePerCapita = fineInZipCode / zipCodePopulation;
				sortedTotalFinesPerCapitaByZipCode.put(zipCode, truncate(zipCodeFinePerCapita, 4));
			}
		}
		totalFinePerCapitaResults = sortedTotalFinesPerCapitaByZipCode;
		return sortedTotalFinesPerCapitaByZipCode;
	}


	/**
	 * Step 3
	 * This method calculates the average market value for a particular ZIP Code.
	 * It also uses memoization in a hashmap format to pull information more efficiently if called again.
	 * @param zipCode - provided zip code.
	 * @return - the average market value.
	 */
	public int getAverageMarketValue(int zipCode) {
		int average = Integer.parseInt(truncate(getAverage(zipCode, new MarketValue()), 0));
		//Memoization technique
		if (averageMarketValueResults.containsKey(zipCode)) {
			return averageMarketValueResults.get(zipCode);
		} else {
			averageMarketValueResults.put(zipCode, average);
		}
		return average;
	}

	/**
	 * Step 4
	 * This method calculates the average total livable area for a particular ZIP Code.
	 * It also uses memoization in a hashmap format to pull information more efficiently if called again.
	 * @param zipCode - provided zip code.
	 * @return - the average total livable area.
	 */
	public int getAverageTotalLivableArea(int zipCode) {
		int average = Integer.parseInt(truncate(getAverage(zipCode, new TotalLivableAreaValue()), 0));
		//Memoization technique
		if (averageTotalLivableAreaResults.containsKey(zipCode)) {
			return averageTotalLivableAreaResults.get(zipCode);
		} else {
			averageTotalLivableAreaResults.put(zipCode, average);
		}
		return average;
	}

	/**
	 * Helper method for strategy pattern (Steps 3 & 4). This method goes through the property objects array list
	 * and searches for the required values and totals it up along with the number of residencies, and divides those values.
	 * @param zipCode - the zip code to search for.
	 * @param val -  the Value to search for (Whether that is TotalLivableArea or MarketValue)
	 * @return - the average of the value searched for divided by the number of residencies
	 */
	private double getAverage(int zipCode, Value val) {
		int numOfResidencies = 0;
		double total = 0;
		for (Property p : this.properties) {
			if (val.value(p) != null && !val.value(p).isEmpty()
					&& p.getZipCode().equals(String.valueOf(zipCode)))  {
				total += Double.parseDouble(val.value(p));
				numOfResidencies++;
			}
		}
		if (numOfResidencies == 0) {
			return 0;
		}
		return total/numOfResidencies;
	}


	/**
	 * Step 5
	 * Total market value per capita and returns the market value provided with a zip code.
	 * @param zipCode - the zip code provided.
	 * @return - the market value per capita in truncated int format.
	 */
	public int getMarketValuePerCapita(String zipCode) {
		//Memoization technique
		if(marketValuePerCapitaResults.containsKey(zipCode)) {
			return marketValuePerCapitaResults.get(zipCode);
		}
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
		for (Property p : this.properties) {
			//if zipcode value matches, add to totalMarketValue
			if (p.getZipCode().equals(zipCode)) {
				totalMarketValue += Double.parseDouble(p.getMarketValue());
			}
		}
		double val = totalMarketValue/populationOfZipCode;
		int finalValue = Integer.parseInt(truncate(val, 0));
		marketValuePerCapitaResults.put(zipCode, finalValue);
		return finalValue;
	}

	/**
	 * Step 6
	 * Display the zip code with the lowest ticket number per capita within the user budget and stores
	 * the information in hashmap format.
	 * @return - a hashmap of zip codes and the ticket number per capita
	 */
	public HashMap<String, Double> safeMethod(double budget) {
		//Memoization technique
		if(safeMethodResults.containsKey(budget)) {
			return safeMethodResults.get(budget);
		}
		else {
			//get number of tickets per ZIP code
			HashMap<String, Integer> ticketsHashMap = totalTicketsPerZipCode();
			//get population for all ZIP codes
			HashMap<Integer, Double> popHashMap = populationInHashMapForm();
			HashMap<String, Double> safeZipCodeTreeMap = new HashMap<>();

			//loop through all the ZIP codes in the population file
			for (Map.Entry<Integer, Double> entry : popHashMap.entrySet()) {
			    String zipCode = Integer.toString(entry.getKey());
				Double popForZipCode = entry.getValue();

				int ticketNumber;
				//check to see if the ZIP code has a total ticket # in the ticketsHashmap
				if (!ticketsHashMap.containsKey(zipCode)) {
					//if there was no ticket for that ZIP code, set the ticket number to 0
					ticketNumber = 0;
				}
				else {
					ticketNumber = ticketsHashMap.get(zipCode);
				}
				//get the average market value for that ZIP code
				int marketValue = getAverageMarketValue(Integer.parseInt(zipCode));
				Double ticketPerCapita = ticketNumber / popForZipCode;
				if (marketValue > budget) {
					continue;
				} else {
					safeZipCodeTreeMap.put(zipCode, ticketPerCapita);
				}
			}
			safeMethodResults.put(budget, safeZipCodeTreeMap);
			return safeZipCodeTreeMap;
		}
	}

}
