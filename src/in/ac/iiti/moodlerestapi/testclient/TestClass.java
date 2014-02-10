package in.ac.iiti.moodlerestapi.testclient;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.ws.rs.client.ClientBuilder;

/**
* REST MOODLE Client
* It's very basic. You'll have to write the JavaObject2POST code.
*
* @author Jerome Mouneyrac jerome@moodle.com
*/
public class TestClass {
	
	public void testProperty(){
    	Properties prop = new Properties();
    	 
    	try {
    		//set the properties value
    		prop.setProperty("database", "localhost");
    		prop.setProperty("dbuser", "mkyong");
    		prop.setProperty("dbpassword", "password");
 
    		//save properties to project root folder
    		prop.store(new FileOutputStream("config.properties"), null);
 
    		Properties prop1 = new Properties();
    		prop1.load(new FileInputStream("config.properties"));
    		 
            //get the property value and print it out
             System.out.println(prop1.getProperty("database"));
 		     System.out.println(prop1.getProperty("dbuser"));
 		     System.out.println(prop1.getProperty("dbpassword"));
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
	}
	
	public void testLogin1(String username,String password){
		String moodleServerUrl = "http://localhost/testmoodle"; //TODO put it at a central location
	       String moodleWebServiceShortname = "alanturing"; //TODO put it at a central location
	       System.out.println("requested url is"+ moodleServerUrl+"/login/token.php?username="+username+"&password="+password+"&service="+moodleWebServiceShortname);
	}

    /**
* Do a REST call to Moodle. Result are displayed in the console log.
* @param args the command line arguments
*/
    public static void main(String[] args) throws ProtocolException, IOException {
          new TestClass().testProperty();    	
//         
//        /// NEED TO BE CHANGED
//        String token = "c58818df1be7c557e5552a4142903eb8";
//        String domainName = "http://localhost/testmoodle";
//
//        /// REST RETURNED VALUES FORMAT
//        String restformat = "json"; //Also possible in Moodle 2.2 and later: 'json'
//                                   //Setting it to 'json' will fail all calls on earlier Moodle version
//        if (restformat.equals("json")) {
//            restformat = "&moodlewsrestformat=" + restformat;
//        } else {
//            restformat = "";
//        }
//
//        /// PARAMETERS - NEED TO BE CHANGED IF YOU CALL A DIFFERENT FUNCTION
//        String functionName = "core_webservice_get_site_info";//"core_user_get_users";
//  
//        String urlParameters = "moodlewsrestformat=json"; // "moodlewsrestformat=json";
//        /// REST CALL
//
//        // Send request
//        String serverurl = domainName + "/webservice/rest/server.php" + "?wstoken=" + token + "&wsfunction=" + functionName;
//        HttpURLConnection con = (HttpURLConnection) new URL(serverurl).openConnection();
//        con.setRequestMethod("GET");
////        con.setRequestProperty("Content-Type",
////           "application/x-www-form-urlencoded");
//       // con.setRequestProperty("Content-Language", "en-US");
//        con.setDoOutput(true);
//        con.setUseCaches (false);
//        con.setDoInput(true);
//        DataOutputStream wr = new DataOutputStream (
//                  con.getOutputStream ());
//        wr.writeBytes (urlParameters);
//        wr.flush ();
//        wr.close ();
//
//        //Get Response
//        InputStream is =con.getInputStream();
//        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
//        String line;
//        StringBuilder response = new StringBuilder();
//        while((line = rd.readLine()) != null) {
//            response.append(line);
//            response.append('\r');
//        }
//
//        JsonReader response1 = Json.createReader(new StringReader(response.toString()));;
//        JsonObject jsonObject = response1.readObject();
//        System.out.println("json response is :\n"+jsonObject.toString());
//        rd.close();
//        System.out.println(response.toString());
    }
}