package edu.upenn.cit594.datamanagement;
import edu.upenn.cit594.data.ParkingViolationObject;

import java.util.ArrayList;
import java.util.List;


public interface Reader {
	
	ArrayList<ParkingViolationObject> getParkingViolationObjects();

}

