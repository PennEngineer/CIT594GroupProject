package edu.upenn.cit594.datamanagement;
import edu.upenn.cit594.data.ParkingViolationObject;

import java.util.List;


public interface Reader {
	
	List<ParkingViolationObject> getParkingViolationObjects();

}

