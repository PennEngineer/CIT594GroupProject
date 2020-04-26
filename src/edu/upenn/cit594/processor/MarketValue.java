package edu.upenn.cit594.processor;

import java.util.HashMap;
import java.util.Map;
import edu.upenn.cit594.data.Property;


public class MarketValue implements Value {

	private Map<Integer, Double> results = new HashMap<>();
	
	public String value(Property p) {
		return p.getMarketValue();
	}
}


