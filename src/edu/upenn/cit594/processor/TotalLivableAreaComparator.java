package edu.upenn.cit594.processor;

import java.util.ArrayList;

import edu.upenn.cit594.data.Property;

public class TotalLivableAreaComparator implements AverageComparator {

	@Override
	public double getAverage(ArrayList<Property> properties, int zipCode) {
		int residentials = 0;
		double total = 0;
		for(Property propertyObject : properties) {
			if(propertyObject.getZipCode().contains(Integer.toString(zipCode))) {
				if(propertyObject.getTotalLivableArea() == null || propertyObject.getTotalLivableArea().equals("") || propertyObject.getTotalLivableArea().isEmpty()) {
					continue;
				}else {
					total += Double.parseDouble(propertyObject.getTotalLivableArea());
					residentials++;
				}
			}
		}
		if(total ==0) return 0;
		return total/residentials;
	}
}
