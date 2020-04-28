package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.Property;
import edu.upenn.cit594.logging.Logger;

import java.io.*;
import java.util.ArrayList;

/**
 * This class is made to read the CSV file of properties file and helps manage the data into an array list for
 * ease of processing.
 */
public class CSVPropertyReader {

    protected String inputFile;

    /**
     * Constructor for the CSVPropertyReader
     * @param fileName - file name that is to be read
     */
    public CSVPropertyReader(String fileName) {
        this.inputFile = fileName;
    }


    /**
     * This method reads the CSV File, sanitizes the data we need (zip code value, market value, and total
     * livable area) and stores it in an array list. We start by reading the first row for the required values that we need,
     * and then targeting those columns using a regex that splits the values we need into a String array. Then, we create the
     * Property object with those values.
     * @return - an array list of Property objects
     */
    public ArrayList<Property> getPropertyObjects() {
        ArrayList<Property> properties = new ArrayList<>();
        try {
            File f = new File(inputFile);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            Logger.getInstance().log(Long.toString(System.currentTimeMillis()) + " " + inputFile);

            String[] firstRow = br.readLine().split(",");
            int marketValue = 0;
            int zipCode = 0;
            int totalLivableArea = 0;
            for (int i = 0; i < firstRow.length; i++) {
                if (firstRow[i].equals("market_value")) {
                    marketValue = i;
                }
                if (firstRow[i].equals("zip_code")) {
                    zipCode = i;
                }
                if (firstRow[i].equals("total_livable_area")) {
                    totalLivableArea = i;
                }
            }
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                String zipCodeValue = fields[zipCode];
                String marketVal = fields[marketValue];
                String totalLivableValue = fields[totalLivableArea];
                if (zipCodeValue != null && zipCodeValue.length() >= 5 && marketVal != null && totalLivableValue != null
                && !marketVal.isEmpty() && !totalLivableValue.isEmpty()) {
                    Property p = new Property(fields[marketValue], fields[totalLivableArea], zipCodeValue.substring(0,5));
                    properties.add(p);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File was not found.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }


}
