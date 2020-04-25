package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import edu.upenn.cit594.data.PopulationObject;
import edu.upenn.cit594.logging.Logger;

/**
 * This class is made to read the populations text file.
 */
public class PopulationFileReader {
	
	protected String inputFile;

	/**
	 * Constructor for the population text file reader.
	 * @param inputFile - the name of the population input file.
	 */
	public PopulationFileReader(String inputFile) {
		this.inputFile = inputFile;
	}

	/**
	 * This method reads the population text file and stores each row of the file in a PopulationObject, which is
	 * stored in an array list format.
	 * @return - an array list of PopulationObjects.
	 */
	public ArrayList<PopulationObject> getPopulationObjects() {
		ArrayList<PopulationObject> populationObjects  = new ArrayList<>();
		File inFile = new File(inputFile);
		try {
			Scanner in = new Scanner(inFile,"UTF-8");
			Logger.getInstance().log(Long.toString(System.currentTimeMillis()) + "  File: " + inputFile);
			while(in.hasNextLine()) {
				String temp = in.nextLine();
				try {
					String[] tempArray = temp.split(" ");
					PopulationObject object = new PopulationObject(tempArray[0], tempArray[1]);
					populationObjects.add(object);

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
		return populationObjects;
	}
}
