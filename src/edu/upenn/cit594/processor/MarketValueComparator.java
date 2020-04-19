package edu.upenn.cit594.processor;

import java.util.ArrayList;
import edu.upenn.cit594.data.Property;

public class MarketValueComparator implements AverageComparator {

	@Override
	public double getAverage(ArrayList<Property> properties, int zipCode) {
		int residentials = 0;
		double total = 0;
		for(Property propertyObject : properties) {
			if(propertyObject.getZipCode().contains(Integer.toString(zipCode))) {
				if(propertyObject.getMarketValue() == null || propertyObject.getMarketValue().equals("") || propertyObject.getMarketValue().isEmpty()) {
					continue;
				}else {
					total += Double.parseDouble(propertyObject.getMarketValue());
					residentials++;
				}
			}
		}
		System.out.println("total " + total);
		System.out.println("residentials " + residentials);
		if(total == 0) return 0;
		return total/residentials;
	}
}
