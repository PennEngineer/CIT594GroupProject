package edu.upenn.cit594.processor;

import java.util.ArrayList;
import java.util.List;

import edu.upenn.cit594.data.ParkingViolationObject;
import edu.upenn.cit594.data.PopulationObject;
import edu.upenn.cit594.data.Property;
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
		for(PopulationObject p : populations) {
			total += Integer.parseInt(p.getPopulationString());
		}
		return total;
	}






	
}
