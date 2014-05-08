package in.ac.iiti.moodlerestapi.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class AppConfigProperty{
     // eager initialization of instance  
	 private static AppConfigProperty configInstance = new AppConfigProperty(); 
	 
	 private static Properties propertyInstance;
	 private static Properties emailInstance;
	 private AppConfigProperty() {
		super();
		try {
			propertyInstance = new Properties();
	        propertyInstance.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));
	        emailInstance = new Properties();
	        emailInstance.load(this.getClass().getClassLoader().getResourceAsStream("email.properties"));
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("log file could not be loaded");
	    }
	 }
	 
	 public static Properties getPropertyInstance(){
		 return AppConfigProperty.propertyInstance;
	 }
	 public static Properties getEmailInstance(){
		 return AppConfigProperty.emailInstance;
	 }
	 public static AppConfigProperty getAppConfigPropertyInstance(){
		 return configInstance;
	 } 
	 public void updateProperties(HashMap<String,String> properties){
		 Iterator<?> iterator = properties.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry mapEntry = (Map.Entry) iterator.next();
				propertyInstance.setProperty((String)mapEntry.getKey(), (String)mapEntry.getValue()); //TODO if this updates
				
		    }
	       
     }
}