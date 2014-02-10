package in.ac.iiti.moodlerestapi.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class AppConfigProperty{
     // eager initialization of instance  
	 private static AppConfigProperty configInstance = new AppConfigProperty(); 
	 
	 private static Properties propertyInstance;
	 
	 private AppConfigProperty() {
		super();
		try {
			propertyInstance = new Properties();
	        propertyInstance.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));
	        System.out.println("in appConfig constructor");
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("file could not be loaded");
	    }
		
	 }
	 
	 public static Properties getPropertyInstance(){
		 return AppConfigProperty.propertyInstance;
	 }
	 public static AppConfigProperty getAppConfigPropertyInstance(){
		 return configInstance;
	 } 
	 
	 public void updateProperties(HashMap<String,String> properties){
		 Iterator<?> iterator = properties.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry mapEntry = (Map.Entry) iterator.next();
				propertyInstance.setProperty((String)mapEntry.getKey(), (String)mapEntry.getValue()); //TODO if this updates
				System.out.println("\nupdated "+ (String)mapEntry.getKey()+" = "+ (String)mapEntry.getValue());
		    }
	       System.out.println("new val "+ propertyInstance.getProperty("moodleServerUrl"));
     }
		
}
