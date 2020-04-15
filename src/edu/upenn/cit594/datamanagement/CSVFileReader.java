package edu.upenn.cit594.datamanagement;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import edu.upenn.cit594.data.ParkingViolationObject;

public class CSVFileReader implements Reader {
	
	protected String inputFile;
	protected String statesFile;
	protected List<ParkingViolationObject> violationObjects = new ArrayList<ParkingViolationObject>();
	
	
	public CSVFileReader(String inputFile) {
		this.inputFile = inputFile;
	}
	
	@Override
	public List<ParkingViolationObject> getAllObjects() {
		File inFile = new File(inputFile);
		try {
			Scanner in = new Scanner(inFile,"UTF-8");
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
