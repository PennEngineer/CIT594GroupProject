package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import edu.upenn.cit594.data.PopulationObject;

public class PopulationFileReader implements Reader {
	
	protected String inputFile;
	protected List<PopulationObject> populationObjects  = new ArrayList<PopulationObject>();
	
	public PopulationFileReader(String inputFile) {
		this.inputFile = inputFile;
	}

	@Override
	public List<PopulationObject> getAllObjects() {
		File inFile = new File(inputFile);
		try {
			Scanner in = new Scanner(inFile,"UTF-8");
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
