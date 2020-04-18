package edu.upenn.cit594.ui;

import edu.upenn.cit594.processor.Processor;

import java.util.Scanner;
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
        System.out.println("Custom feature, enter 6.");
        int choice = in.nextInt();
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
    }

    //choice 2
    protected void doParkingFinesPerCapitaForAllZipCodes() {
        //calculate "ZIPCODE fine", i.e. 19103 0.0284 (truncated)
    }

    //choice 3
    protected void doAverageResidentialMarketValue() {
        System.out.println("Please enter a ZIP code.");
        //if user enters an incorrect zip code, or a zip code not valid in the input files, display 0.
        int zipcodeChoice = in.nextInt();


    }

    //choice 4
    protected void doAverageTotalLivableArea() {

    }

    //choice 5
    protected void doTotalResidentialMarketValuePerCapita() {

    }

    //choice 6
    protected void doCustom() {

    }


}