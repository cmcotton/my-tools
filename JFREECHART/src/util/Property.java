package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Property {
	
	public static String getProperty(String key) {
		String value = "";
		
		Properties properties = new Properties();
		String configFile = "config.properties";
		try {
		    properties.load(new FileInputStream(configFile));
		    value = (String) properties.get(key);
		} catch (FileNotFoundException ex) {
		    ex.printStackTrace();		    
		} catch (IOException ex) {
		    ex.printStackTrace();
		}
		
		return value; 
	}

}
