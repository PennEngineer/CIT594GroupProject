package edu.upenn.cit594.logging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This is a logger class based on the Singleton design pattern.
 */
public class Logger {

    private static Logger instance;
    private PrintWriter out;
    private FileWriter fileWriter;

    //1. Private constructor
    private Logger(String fileName) {

    	try {
			fileWriter = new FileWriter(new File(fileName), true);
			out = new PrintWriter(fileWriter);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
    }

    /**
     * This method is called to initialize the instance with the file name for the logger.
     * @param logFileName
     */
    public static void initializeName(String logFileName) {

        instance = new Logger(logFileName);
    }

    /**
     * This method calls the instance of the logger class.
     * @return the logger instance
     */
    public static Logger getInstance() {
        return instance;
    }

    /**
     * This method logs the messages passed into the parameter during processing.
     * @param msg the message needed to be logged
     */
    public void log(String msg) {
        out.println(msg);
        out.flush();
    }

}

