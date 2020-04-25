package edu.upenn.cit594.data;

/**
 * This class represents a parking violation object that was read in from the parking violation file.
 */
public class ParkingViolationObject {
	private final String timeStamp;
	private final String fine;
	private final String description;
	private final String vehicleIdentifier;
	private final String state;
	private final String ticketIdentifier;
	private final String zipcode;

	/**
	 * Constructor for a Parking Violation Object.
	 * @param timeStamp - the time of violation
	 * @param fine - the cost of the violation
	 * @param description - the reason for violation
	 * @param vehicleIdentifier - vehicle identification number
	 * @param state - the state license plate
	 * @param ticketIdentifier - the ticket identification number
	 * @param zipcode - the zipcode area in which the violation occurred
	 */
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


	//getters and setters for private instance variables of the parking violation object below
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
