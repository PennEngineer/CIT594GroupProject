package edu.upenn.cit594.ui;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.MarketValue;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.processor.TotalLivableAreaValue;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandLineUserInterface {

    protected Processor processor;
    protected Scanner in;

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


    //choice 1
    protected void doTotalPopulationForAllZipCodes() {
        //calculate total population for all ZIP codes
        System.out.println(processor.calculatePopulation());
        //re-prompt user
        start();
    }

    //choice 2
    protected void doParkingFinesPerCapitaForAllZipCodes() {
        //calculate "ZIPCODE fine", i.e. 19103 0.0284 (truncated)
        for (Integer zip : processor.totalFinePerCapita().keySet()) {
            System.out.println(zip + " " + processor.totalFinePerCapita().get(zip));
        }

        //re-prompt user
        start();
    }

    //choice 3
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

    //choice 4
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

    //choice 5
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

    //choice 6
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
    
    //helper method to check if the number is a number
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
