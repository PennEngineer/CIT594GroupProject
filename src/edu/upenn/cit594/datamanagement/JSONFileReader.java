package edu.upenn.cit594.datamanagement;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import edu.upenn.cit594.data.*;
import edu.upenn.cit594.logging.Logger;

/**
 * This class is made to read the JSON version of the Parking violations file.
 */
public class JSONFileReader implements Reader {
	
	protected String inputFile;

	/**
	 * Constructor for the JSONFileReader
	 * @param inputFile - the name of the input file to be read
	 */
	public JSONFileReader(String inputFile) {
		this.inputFile = inputFile;
	}

	/**
	 * This method reads and sanitizes the data for the JSON version of the Parking Violations file and
	 * stores each row as a Parking violation object in an array list.
	 * @return - an array list of parking violation objects
	 */
	public ArrayList<ParkingViolationObject> getParkingViolationObjects() {
		ArrayList<ParkingViolationObject> violationObjects = new ArrayList<>();
		// create a parser
		JSONParser parser = new JSONParser();
		
		try {
			FileReader r = new FileReader(inputFile);
			Logger.getInstance().log(Long.toString(System.currentTimeMillis()) + "  File: " + inputFile);
			Object obj = parser.parse(r);
			JSONArray violationJson = (JSONArray) obj;
			for (int i = 0; i < violationJson.size(); i++) {
					JSONObject a = (JSONObject) violationJson.get(i);
					ParkingViolationObject violationObject = new ParkingViolationObject( (String) a.get("date").toString(), (String) a.get("fine").toString(),(String) a.get("violation").toString(),(String) a.get("plate_id").toString(),(String) a.get("state").toString(),(String) a.get("ticket_number").toString(),(String) a.get("zip_code").toString());
					violationObjects.add(violationObject);
				}
		} catch (Exception e) {
			System.out.println("The input file does not exist.");
		}
		return violationObjects;
	}
}
