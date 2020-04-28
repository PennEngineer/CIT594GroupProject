package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Property;

/**
 * This is the MarketValue class that implements the Value interface for abstraction. It is used to
 * search for the Market Value.
 */
public class MarketValue implements Value {

	public String value(Property p) {
		return p.getMarketValue();
	}
}


