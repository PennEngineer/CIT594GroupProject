package edu.upenn.cit594.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import edu.upenn.cit594.data.Property;

public class TotalLivableAreaValue implements Value {

//	private Map<Integer, Double> results = new HashMap<>();

//	@Override
//	public double getAverage(ArrayList<Property> properties, int zipCode) {
//
//		if(results.containsKey(zipCode)) {
//			return results.get(zipCode);
//		}
//
//		else {
//			int residentials = 0;
//			double total = 0;
//			for(Property propertyObject : properties) {
//				if(propertyObject.getZipCode().contains(Integer.toString(zipCode))) {
//					if(propertyObject.getTotalLivableArea() == null || propertyObject.getTotalLivableArea().equals("") || propertyObject.getTotalLivableArea().isEmpty()) {
//						continue;
//					} else {
//						total += Double.parseDouble(propertyObject.getTotalLivableArea());
//						residentials++;
//					}
//				}
//			}
//			if(total ==0) {
//				results.put(zipCode, 0.0);
//				return 0;
//			}
//			double val = total/residentials;
//			results.put(zipCode, val);
//			return val;
//		}
//	}
	public String value(Property p) {
		return p.getTotalLivableArea();
	}


}