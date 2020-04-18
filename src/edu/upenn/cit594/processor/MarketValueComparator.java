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
//					System.out.println("Null String:        " + propertyObject.getZipCode() + " " + propertyObject.getMarketValue());
					continue;
				}else {
//					System.out.println(propertyObject.getZipCode() + "    " + propertyObject.getMarketValue());
					total += Double.parseDouble(propertyObject.getMarketValue());
					residentials++;
				}
			}
		}
//		System.out.println("total: " + total);
//		System.out.println("residentials: " + residentials);
		return total/residentials;
	}
}
