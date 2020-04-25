package edu.upenn.cit594.datamanagement;
import edu.upenn.cit594.data.ParkingViolationObject;

import java.util.ArrayList;


/**
 * This is an interface to help abstract the parking violation file readers which could be JSON or CSV.
 */
public interface Reader {
	
	ArrayList<ParkingViolationObject> getParkingViolationObjects();

}

