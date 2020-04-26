package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Property;


public class MarketValue implements Value {

	public String value(Property p) {
		return p.getMarketValue();
	}
}


