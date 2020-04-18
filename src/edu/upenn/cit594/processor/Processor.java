package edu.upenn.cit594.processor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	private static String truncate(String value, int places) {
		return new BigDecimal(value)
				.setScale(places, RoundingMode.DOWN)
				.stripTrailingZeros()
				.toString();
	}


	//step 2 -- calculate total fine per capita
	//use of tree map to sort key of zipcodes
	public TreeMap<Integer, Double> totalFinePerCapita() {
		TreeMap<Integer, Double> sortedTotalFinesPerCapitaByZipCode = new TreeMap<>();
		for (Integer zipCode : totalAggregateFineByZipCode().keySet()) {
			if (populationInHashMapForm().containsKey(zipCode)) {
				double zipCodePopulation = populationInHashMapForm().get(zipCode);
				double fineInZipCode = totalAggregateFineByZipCode().get(zipCode);
				double zipCodeFinePerCapita = fineInZipCode / zipCodePopulation;
				String stringForm = truncate(String.valueOf(zipCodeFinePerCapita), 4);
				sortedTotalFinesPerCapitaByZipCode.put(zipCode, Double.parseDouble(stringForm));
			}
		}
		return sortedTotalFinesPerCapitaByZipCode;
	}
	
	//step 3 & 4 -- calculate average market value or total livable area by number of residences
	public double getAverage(AverageComparator comparator, int zipcode) {
		
		return comparator.getAverage(properties, zipcode);
	}





	//used for testing -- delete after
//	public static void main(String[] args) {
//		//must import properties file
//		CSVPropertyReader cv = new CSVPropertyReader("properties.csv");
//		Reader r = new JSONFileReader("parking.json");
//		PopulationFileReader pr = new PopulationFileReader("population.txt");
//		Processor p = new Processor(r, pr.getPopulationObjects(), cv.getPropertyObjects());
//		for (Integer zip : p.totalFinePerCapita().keySet()) {
//			System.out.println(zip + " " + p.totalFinePerCapita().get(zip));
//		}
//	}
	//values I got from the program running: (took 1 min 50 secs)
//	        19102 6.9347
//			19103 4.9118
//			19104 1.2435
//			19106 4.1227
//			19107 6.2616
//			19111 0.0401
//			19114 0.0021
//			19118 0.3168
//			19119 0.0609
//			19120 0.1605
//			19121 0.2897
//			19122 0.1353
//			19123 1.9564
//			19124 0.174
//			19125 0.1098
//			19126 0.0764
//			19127 1.9611
//			19128 0.0797
//			19129 0.3235
//			19130 1.6743
//			19131 0.0532
//			19132 0.1774
//			19133 0.1458
//			19134 0.114
//			19135 0.0912
//			19136 0.0546
//			19138 0.0215
//			19139 0.4214
//			19140 0.3243
//			19141 0.4757
//			19142 0.1878
//			19143 0.0909
//			19144 0.2852
//			19145 0.295
//			19146 0.9006
//			19147 1.8546
//			19148 0.3692
//			19149 0.0859
//			19150 0.0306
//			19151 0.0505
//			19152 0.0364
//			19153 0.0325
	
}
