package edu.upenn.cit594.processor;

import java.util.ArrayList;

import edu.upenn.cit594.data.Property;

public class TotalLivableAreaComparator implements AverageComparator {

	@Override
	public double getAverage(ArrayList<Property> properties, int zipCode) {
		int residentials = 0;
		int total = 0;
		for(Property propertyObject : properties) {
			if(Integer.parseInt(propertyObject.getZipCode()) == zipCode) {
				total += Integer.parseInt(propertyObject.getTotalLivableArea());
				residentials++;
			}
		}
		return total/residentials;
	}

}
