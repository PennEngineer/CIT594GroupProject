package edu.upenn.cit594;

import java.io.File;
import edu.upenn.cit594.data.PropertyObject;

public class Main {
	
	static String logName;

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
		
		logName = args[4];
		
	}

}
