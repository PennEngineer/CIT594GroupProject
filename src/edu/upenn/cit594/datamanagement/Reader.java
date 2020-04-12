package edu.upenn.cit594.datamanagement;

import java.util.List;
import edu.upenn.cit594.data.ParkingViolationObject;



public interface Reader {
	
	public List<ParkingViolationObject> getAllParkingViolationObjects();

}

