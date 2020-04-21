package edu.upenn.cit594.ui;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.MarketValueComparator;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.processor.TotalLivableAreaComparator;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        System.out.println("To show a list of ticket number per capita for ZIP codes within your budget, enter 6.");
        int choice = in.nextInt();
        Logger.getInstance().log(Long.toString(System.currentTimeMillis()) + "  User Selection: " + choice);
        
        if (choice == 0) {
            System.exit(1);
        } else if (choice == 1) {
            doTotalPopulationForAllZipCodes();
        } else if (choice == 2) {
            doParkingFinesPerCapitaForAllZipCodes();
        } else if (choice == 3) {
            doAverageResidentialMarketValue();
        } else if (choice == 4) {
            doAverageTotalLivableArea();
        } else if (choice == 5) {
            doTotalResidentialMarketValuePerCapita();
        } else if (choice == 6) {
            doCustom();
        } else {
            System.err.println("Please enter a number between 0 and 6.");
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
        int zipcodeChoice = in.nextInt();
        Logger.getInstance().log(Long.toString(System.currentTimeMillis()) + "  User Selection: " + zipcodeChoice);
        System.out.println(processor.getAverage(new MarketValueComparator(), zipcodeChoice));

        //re-prompt user
        start();
    }

    //choice 4
    protected void doAverageTotalLivableArea() {
        System.out.println("Please enter a ZIP code.");
        //if user enters an incorrect zip code, or a zip code not valid in the input files, display 0.
        int zipcodeChoice = in.nextInt();
        Logger.getInstance().log(Long.toString(System.currentTimeMillis()) + "  User Selection: " + zipcodeChoice);
        System.out.println(processor.getAverage(new TotalLivableAreaComparator(), zipcodeChoice));

        //re-prompt user
        start();
    }

    //choice 5
    protected void doTotalResidentialMarketValuePerCapita() {
        System.out.println("Please enter a ZIP code.");
        //if user enters an incorrect zip code, or a zip code not valid in the input files, display 0.
        int zipcodeChoice = in.nextInt();
        Logger.getInstance().log(Long.toString(System.currentTimeMillis()) + "  User Selection: " + zipcodeChoice);
        String valueOfZip = String.valueOf(zipcodeChoice);
        System.out.println(processor.getMarketValuePerCapita(valueOfZip));

        //re-prompt user
        start();
    }

    //choice 6
    protected void doCustom() {
    	System.out.println("Please enter your budget to find the list ZIP codes with their ticket number per capita within your budget.");
        double budget = in.nextDouble();
        TreeMap<String, Double> resultsMap = processor.safeMethod(budget);
        for (Map.Entry<String,Double> entry : resultsMap.entrySet()) {
        	System.out.println("ZIP code: " + entry.getKey() + "    Ticket Number Per Capita: "+ entry.getValue());
       }
        
      //re-prompt user
        start();
    }


}
