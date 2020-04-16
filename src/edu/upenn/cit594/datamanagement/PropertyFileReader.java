package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import edu.upenn.cit594.data.PropertyObject;

public class PropertyFileReader implements Reader{
	
	private String file;
	protected List<PropertyObject> propertyObjects  = new ArrayList<>();
	
	public PropertyFileReader(String file) {
		this.file = file;	
	}

	@Override
	public List<PropertyObject> getAllObjects() {
		
		File input = new File(file);
		
		CsvParserSettings parserSettings = new CsvParserSettings();
		parserSettings.getFormat().setLineSeparator("\n");
		parserSettings.selectFields("market_value", "total_livable_area", "zip_code");
		CsvParser parser = new CsvParser(parserSettings);
		List<String[]> parsedRows = parser.parseAll(input);
		for(String[] s:parsedRows) {
			String one = s[0];
			String two = s[1];
			String three = s[2];
			if(three != null) {
				if(three.length() >=5) {
					three = three.substring(0,5);
				}
			}
			PropertyObject propertyObject = new PropertyObject(one, two, three);
			propertyObjects.add(propertyObject);	
		}
		return propertyObjects;
	}

}
