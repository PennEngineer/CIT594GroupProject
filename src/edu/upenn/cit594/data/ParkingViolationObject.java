package edu.upenn.cit594.data;

public class ParkingViolationObject {
	private String timeStamp;
	private String fine;
	private String description;
	private String identifier;
	private String state;
	private String zipcode;
	
	public ParkingViolationObject(String timeStamp, String fine, String description, String identifier, String state, String zipcode) {
		this.timeStamp = timeStamp;
		this.fine = fine;
		this.description = description;
		this.identifier = identifier;
		this.state = state;
		this.zipcode = zipcode;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public String getFine() {
		return fine;
	}

	public String getDescription() {
		return description;
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getState() {
		return state;
	}

	public String getZipcode() {
		return zipcode;
	}
}
