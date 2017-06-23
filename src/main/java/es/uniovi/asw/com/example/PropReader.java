package es.uniovi.asw.com.example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropReader {

	private static Properties prop;
	
	private static void setPropObject() throws FileNotFoundException, IOException{
		if (prop == null){
			prop = new Properties();
			prop.load(new FileInputStream("psysconf.properties"));
			}
	}
	
	public static String get(String property){
		try {
			setPropObject();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("ERROR: the initial configuration file was not found. Aborting...");
		} catch (IOException e) {
			System.out.println("Configuration file error");
			return null;
		}
		
		return prop.getProperty(property);
	}
	
	static void set(String property, String value){
		try {
			setPropObject();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("ERROR: the initial configuration file was not found. Aborting...");
		} catch (IOException e) {
			System.out.println("Configuration file error");
		}
		
		prop.setProperty(property, value);
	}
}
