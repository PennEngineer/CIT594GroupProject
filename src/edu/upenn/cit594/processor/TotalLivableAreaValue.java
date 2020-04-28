package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Property;


/**
 * This is the TotalLivableAreaValue class that implements the Value interface for abstraction. It is used to
 * search for the TotalLivableAreaValue.
 */
public class TotalLivableAreaValue implements Value {

	public String value(Property p) {
		return p.getTotalLivableArea();
	}

}