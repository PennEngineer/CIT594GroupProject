package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import edu.upenn.cit594.data.populationObject;

public class populationFileReader {
	
	protected String inputFile;
	protected List<populationObject> populationObjects  = new ArrayList<populationObject>();
	
	public populationFileReader(String inputFile) {
		this.inputFile = inputFile;
	}
	
	public List<populationObject> getAllPopulationObjects() {
		File inFile = new File(inputFile);
		try {
			Scanner in = new Scanner(inFile,"UTF-8");
			while(in.hasNextLine()) {
				String temp = in.nextLine();
				try {
					String[] tempArray = temp.split(" ");
					populationObject object = new populationObject(tempArray[0], tempArray[1]);
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
