package edu.upenn.cit594.processor;

import java.util.ArrayList;
import edu.upenn.cit594.data.Property;

public class MarketValueComparator implements AverageComparator {

	@Override
	public double getAverage(ArrayList<Property> properties, int zipCode) {
		int residentials = 0;
		int total = 0;
		for(Property propertyObject : properties) {
			if(Integer.parseInt(propertyObject.getZipCode()) == zipCode) {
				total += Integer.parseInt(propertyObject.getMarketValue());
				residentials++;
			}
		}
		return total/residentials;
	}
}
