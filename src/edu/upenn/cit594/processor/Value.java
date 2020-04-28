package edu.upenn.cit594.processor;
import java.util.ArrayList;
import edu.upenn.cit594.data.Property;

/**
 * This is an interface that utilizes the strategy design pattern which helps with
 * increasing the reusability of the code. It is implemented by the MarketValue class and the TotalLivableAreaValue class.
 */
public interface Value {

	String value(Property p);

}
