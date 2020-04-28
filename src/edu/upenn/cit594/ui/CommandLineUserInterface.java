package edu.upenn.cit594.ui;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.MarketValue;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.processor.TotalLivableAreaValue;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * This class is the User Interface tier for the program. It helps process the information that is displayed to the user.
 */
public class CommandLineUserInterface {

    protected Processor processor;
    protected Scanner in;

    /**
     * Constructor for the class, used in the Main class.
     * @param processor - the processor that makes calculations on the data
     */
    public CommandLineUserInterface(Processor processor) {
        this.processor = processor;
        in = new Scanner(System.in);
    }

    public void start() {
        System.out.println("To exit the program, enter 0.");
        System.out.println("To find the total population for all ZIP codes, enter 1.");
        System.out.println("To show the parking fines per capita for each ZIP code, enter 2.");
        System.out.println("To show the average market value for residencies in a specified ZIP code, enter 3.");
        System.out.println("To show the average total livable area for residencies in a specified ZIP code, enter 4.");
        System.out.println("To show the total residential market value per capita for a specified ZIP code, enter 5.");
        System.out.println("To show a list of average tickets # per capita for ZIP codes within your budget, enter 6.");
        String choice = in.next();
        Logger.getInstance().log(Long.toString(System.currentTimeMillis()) + " User Selection: " + choice);
        
        if (choice.equals("0")) {
            System.exit(1);
        } else if (choice.equals("1")) {
            doTotalPopulationForAllZipCodes();
        } else if (choice.equals("2")) {
            doParkingFinesPerCapitaForAllZipCodes();
        } else if (choice.equals("3")) {
            doAverageResidentialMarketValue();
        } else if (choice.equals("4")) {
            doAverageTotalLivableArea();
        } else if (choice.equals("5")) {
            doTotalResidentialMarketValuePerCapita();
        } else if (choice.equals("6")) {
            doCustom();
        } else {
            System.err.println("Input Error. Please enter a number between 0 and 6.");
            System.exit(1);
        }
    }


    /**
     * Choice 1
     * This method displays the total population for all the zip codes in the population.txt file.
     */
    protected void doTotalPopulationForAllZipCodes() {
        //calculate total population for all ZIP codes
        System.out.println(processor.calculatePopulation());
        //re-prompt user
        start();
    }

    /**
     * Choice 2
     * This method prints the total parking fines per capita according to zip code in ascending order.
     */
    protected void doParkingFinesPerCapitaForAllZipCodes() {
        //calculate "ZIPCODE fine", i.e. 19103 0.0284 (truncated)
        for (Integer zip : processor.totalFinePerCapita().keySet()) {
            System.out.println(zip + " " + processor.totalFinePerCapita().get(zip));
        }

        //re-prompt user
        start();
    }

    /**
     * Choice 3
     * This method prints the average residential market value for the zip code that is provided from the user.
     */
    protected void doAverageResidentialMarketValue() {
        System.out.println("Please enter a ZIP code.");
        //if user enters an incorrect zip code, or a zip code not valid in the input files, display 0.
        String zipcodeChoice = in.next();
        Logger.getInstance().log(Long.toString(System.currentTimeMillis()) + " ZIP code: " + zipcodeChoice);
        if (!zipcodeChoice.matches("^[0-9]{5}$")) {
            System.out.println(0);
            start();
        }
        System.out.println(processor.getAverageMarketValue(Integer.parseInt(zipcodeChoice)));

        //re-prompt user
        start();
    }

    /**
     * Choice 4
     * This method prints the average total livable area value for the zip code that is provided from the user.
     */
    protected void doAverageTotalLivableArea() {
        System.out.println("Please enter a ZIP code.");
        //if user enters an incorrect zip code, or a zip code not valid in the input files, display 0.
        String zipcodeChoice = in.next();
        Logger.getInstance().log(Long.toString(System.currentTimeMillis()) + " ZIP code: " + zipcodeChoice);
        if (!zipcodeChoice.matches("^[0-9]{5}$")) {
            System.out.println(0);
            start();
        }
        System.out.println(processor.getAverageTotalLivableArea(Integer.parseInt(zipcodeChoice)));

        //re-prompt user
        start();
    }

    /**
     * Choice 5
     * This method prints the total residential market value per capita according to a zip code that is provided by the user.
     */
    protected void doTotalResidentialMarketValuePerCapita() {
        System.out.println("Please enter a ZIP code.");
        //if user enters an incorrect zip code, or a zip code not valid in the input files, display 0.
        String zipcodeChoice = in.next();
        Logger.getInstance().log(Long.toString(System.currentTimeMillis()) + " ZIP code: " + zipcodeChoice);
        if (!zipcodeChoice.matches("^[0-9]{5}$")) {
            System.out.println(0);
            start();
        }
        System.out.println(processor.getMarketValuePerCapita(zipcodeChoice));

        //re-prompt user
        start();
    }

    /**
     * Choice 6
     * This method prints out the values that is less than or equal to the provided budget from the user. It displays all the zip codes
     * that match that budget and the number of tickets per capita for that zip code.
     */
    protected void doCustom() {
    	System.out.println("Please enter your preferred market value budget.");
        String budget = in.next();
        if(!isNumeric(budget)) {
        	System.out.println("Please try again with numbers only.");
        } else {
        HashMap<String, Double> resultsMap = processor.safeMethod(Double.parseDouble(budget));
        if (resultsMap.size() == 0) System.out.println("There were no ZIP codes within your budget. Please try again with a higher amount.");
        else {
        		System.out.println("ZIP codes with their average ticket # per capita within your budget: " +"$"+budget+"\n");
		        for (Map.Entry<String,Double> entry : resultsMap.entrySet()) {
		        	DecimalFormat df = new DecimalFormat("#.00000");
		        	System.out.println("ZIP code: " + entry.getKey() + "   Average Ticket # Per Capita: " + df.format(entry.getValue()));
		       }
        	}
        }
      //re-prompt user
        start();
    }

    /**
     * This is a helper method to check if a number is a number.
     * @param input - value to be checked.
     * @return - true or false if a number is a number.
     */
    private boolean isNumeric(String input) {
        boolean isNumeric = true;
        try {
            Double.parseDouble(input);

        }catch (NumberFormatException e) {
            isNumeric = false;
        }
        return isNumeric;
    }


}
