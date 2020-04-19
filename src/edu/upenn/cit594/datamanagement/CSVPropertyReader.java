package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.Property;

import java.io.*;
import java.util.ArrayList;

public class CSVPropertyReader {

    protected String inputFile;

    public CSVPropertyReader(String fileName) {
        this.inputFile = fileName;
    }


    public ArrayList<Property> getPropertyObjects() {
        ArrayList<Property> properties = new ArrayList<>();
        try {
            File f = new File(inputFile);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

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
                if (zipCodeValue != null && zipCodeValue.length() >= 5) {
                    Property p = new Property(fields[marketValue], fields[totalLivableArea], zipCodeValue.substring(0,5));
                    properties.add(p);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }


}
