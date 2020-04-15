package edu.upenn.cit594.processor;

import java.util.List;

import edu.upenn.cit594.data.PopulationObject;
import edu.upenn.cit594.datamanagement.Reader;

public class Processor {
	
	protected Reader reader;
	
	
	public Processor(Reader reader) {
		this.reader = reader;
	}
	
	public int calculatePopulation(List<? extends Object> list){
		int total = 0;
		for(Object p:list) {
			total += Integer.parseInt(((PopulationObject) p).getPopulationString());
		}
		return total;
	}

	
}
