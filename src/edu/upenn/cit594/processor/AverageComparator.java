package edu.upenn.cit594.processor;
import java.util.ArrayList;
import edu.upenn.cit594.data.Property;

public interface AverageComparator {
	
	public double getAverage(ArrayList<Property> properties, String zipCode);

}
