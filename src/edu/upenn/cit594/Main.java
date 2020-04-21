package edu.upenn.cit594;


import java.io.File;
import edu.upenn.cit594.logging.*;
import edu.upenn.cit594.datamanagement.CSVFileReader;
import edu.upenn.cit594.datamanagement.CSVPropertyReader;
import edu.upenn.cit594.datamanagement.JSONFileReader;
import edu.upenn.cit594.datamanagement.PopulationFileReader;
import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.ui.CommandLineUserInterface;

public class Main {

	public static void main(String[] args) {
	
		if (args.length != 5) {
			System.out.println("Please enter all 5 arguments.");
			System.exit(0);
		}
		if(!args[0].toLowerCase().equals("json") && !args[0].toLowerCase().equals("csv")) {
			System.out.println("Please specify either json or csv.");
			System.exit(0);
		}
		File input = new File(args[1]);
		if(!input.exists() || !input.canRead()) { 
		    System.out.println("File name: " + args[1] + " does not exist or cannot be read.");
		    System.exit(0);  
		}
		File input2 = new File(args[2]);
		if(!input2.exists() || !input2.canRead()) { 
		    System.out.println("File name: " + args[2] + " does not exist or cannot be read.");
		    System.exit(0);  
		}
		File input3 = new File(args[3]);
		if(!input3.exists() || !input3.canRead()) { 
		    System.out.println("File name: " + args[3] + " does not exist or cannot be read.");
		    System.exit(0);  
		}
		
		Reader reader;
		if(args[0].toLowerCase().equals("csv")) {
			reader = new CSVFileReader(args[1]);
		}
		else {
			reader = new JSONFileReader(args[1]);
		}
		CSVPropertyReader propertyReader = new CSVPropertyReader(args[2]);
		PopulationFileReader populationReader = new PopulationFileReader(args[3]);
		Logger.initializeName(args[4]);
		Processor processor = new Processor(reader, populationReader.getPopulationObjects(), propertyReader.getPropertyObjects());
		CommandLineUserInterface commandLineInterface = new CommandLineUserInterface(processor);
		commandLineInterface.start();
	}

}
