package edu.upenn.cit594.processor;
import java.util.ArrayList;
import edu.upenn.cit594.data.Property;

/**
 * This is an interface that utilizes the strategy design pattern which helps with
 * increasing the reusability of the code.
 */
public interface Value {
	
//	double getAverage(ArrayList<Property> properties, int zipCode);
	String value(Property p);

}
