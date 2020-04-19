package edu.upenn.cit594.logging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * This is a logger class based on the Singleton design pattern.
 */
public class Logger {

    private static Logger instance;
    private PrintWriter out;

    //1. Private constructor
    private Logger(String fileName) {

        try {
            out = new PrintWriter(new File(fileName));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
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

