package edu.upenn.cit594.datamanagement;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import edu.upenn.cit594.data.ParkingViolationObject;
import edu.upenn.cit594.logging.Logger;

/**
 * This class is made to read the CSV file of parking violations and store the information in an array list
 * for ease of processing.
 */
public class CSVFileReader implements Reader {
	
	protected String inputFile;

	/**
	 * Constructor for the CSVFileReader class.
	 * @param inputFile - the filename that represents the parking file.
	 */
	public CSVFileReader(String inputFile) {
		this.inputFile = inputFile;
	}

	/**
	 * This method reads the desired csv parking file and returns an array list of
	 * ParkingViolationObjects, which is represented from each row in the CSV file.
	 * @return - an array list of ParkingViolationObjects
	 */
	public ArrayList<ParkingViolationObject> getParkingViolationObjects() {
		ArrayList<ParkingViolationObject> violationObjects = new ArrayList<>();
		File inFile = new File(inputFile);
		try {
			Scanner in = new Scanner(inFile,"UTF-8");
			Logger.getInstance().log(Long.toString(System.currentTimeMillis()) + " "  + inputFile);
			while(in.hasNextLine()) {
				String temp = in.nextLine();
				try {
					String[] tempArray = temp.split(",");
					if(tempArray.length == 7) {
						ParkingViolationObject violationObject = new ParkingViolationObject( tempArray[0], tempArray[1],tempArray[2],tempArray[3],tempArray[4],tempArray[5],tempArray[6]);
						violationObjects.add(violationObject);
					}else {
						ParkingViolationObject violationObject = new ParkingViolationObject( tempArray[0], tempArray[1],tempArray[2],tempArray[3],tempArray[4],tempArray[5],null);
						violationObjects.add(violationObject);
					}
				} catch (Exception e) {
					System.out.println("An error occurred during reading the file.");
					continue;
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("The input file does not exist.");
			e.printStackTrace();
		}	
		return violationObjects;
	}
}
