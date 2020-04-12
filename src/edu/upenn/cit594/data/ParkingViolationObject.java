package edu.upenn.cit594.data;

public class ParkingViolationObject {
	private final String timeStamp;
	private final String fine;
	private final String description;
	private final String vehicleIdentifier;
	private final String state;
	private final String ticketIdentifier;
	private final String zipcode;
	
	public ParkingViolationObject(String timeStamp, String fine, String description, String vehicleIdentifier,
			String state, String ticketIdentifier, String zipcode) {
		super();
		this.timeStamp = timeStamp;
		this.fine = fine;
		this.description = description;
		this.vehicleIdentifier = vehicleIdentifier;
		this.state = state;
		this.ticketIdentifier = ticketIdentifier;
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

	public String getVehicleIdentifier() {
		return vehicleIdentifier;
	}

	public String getTicketIdentifier() {
		return ticketIdentifier;
	}

	public String getState() {
		return state;
	}

	public String getZipcode() {
		return zipcode;
	}
}
