package edu.upenn.cit594.datamanagement;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import edu.upenn.cit594.data.*;

public class JSONFileReader implements Reader {
	
	protected String inputFile;
	
	public JSONFileReader(String inputFile) {
		this.inputFile = inputFile;
	}

	public ArrayList<ParkingViolationObject> getParkingViolationObjects() {
		ArrayList<ParkingViolationObject> violationObjects = new ArrayList<>();
		// create a parser
		JSONParser parser = new JSONParser();
		
		try {
			FileReader r = new FileReader(inputFile);
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
