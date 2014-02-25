package in.ac.iiti.moodlerestapi.testclient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
* REST MOODLE Client
* It's very basic. You'll have to write the JavaObject2POST code.
*
* @author Jerome Mouneyrac jerome@moodle.com
*/
public class RestJsonMoodleClient {

    /**
* Do a REST call to Moodle. Result are displayed in the console log.
* @param args the command line arguments
*/
	public void getData(){
		String moodleServerUrl = "http://localhost/testmoodle"; //TODO put it at a central location
	       String moodleWebServiceShortname = "alanturing"; //TODO put it at a central location
	       String username = "teacher1cse";
	       String password = "Teacher1cse@123";
	       System.out.println("requested url is"+ moodleServerUrl+"/login/token.php?username="+username+"&password="+password+"&service="+moodleWebServiceShortname);
	}
	
    public static void main(String[] args) throws ProtocolException, IOException {
      
    	/// NEED TO BE CHANGED
        String token = "c58818df1be7c557e5552a4142903eb8";
        String domainName = "http://localhost/testmoodle";

        /// REST RETURNED VALUES FORMAT
        String restformat = "xml"; //Also possible in Moodle 2.2 and later: 'json'
                                   //Setting it to 'json' will fail all calls on earlier Moodle version
        if (restformat.equals("json")) {
            restformat = "&moodlewsrestformat=" + restformat;
        } else {
            restformat = "";
        }

        /// PARAMETERS - NEED TO BE CHANGED IF YOU CALL A DIFFERENT FUNCTION
        String functionName ="core_user_create_users";// "core_course_create_courses";//
        String urlParameters =
        
        "users[0][username]=" + URLEncoder.encode(" phd12120101", "UTF-8") +
        "&users[0][password]=" + URLEncoder.encode("Test@123", "UTF-8") +
        "&users[0][firstname]=" + URLEncoder.encode("testfirstname2", "UTF-8") +
        "&users[0][lastname]=" + URLEncoder.encode("usr", "UTF-8") +
        "&users[0][email]=" + URLEncoder.encode("test1@moodle.com", "UTF-8") +
        "&users[0][timezone]=" + URLEncoder.encode("Pacific/Port_Moresby", "UTF-8") + 
        
        "&users[1][username]=" + URLEncoder.encode(" phd1301201005", "UTF-8") +
        "&users[1][password]=" + URLEncoder.encode("Test@123", "UTF-8") +
        "&users[1][firstname]=" + URLEncoder.encode("testfirstname2", "UTF-8") +
        "&users[1][lastname]=" + URLEncoder.encode("usr", "UTF-8") +
        "&users[1][email]=" + URLEncoder.encode("test2@moodle.com", "UTF-8") +
        "&users[1][timezone]=" + URLEncoder.encode("Pacific/Port_Moresby", "UTF-8") + 

        "&users[2][username]=" + URLEncoder.encode("cs1100108", "UTF-8") +
        "&users[2][password]=" + URLEncoder.encode("Test@123", "UTF-8") +
        "&users[2][firstname]=" + URLEncoder.encode("testfirstname2", "UTF-8") +
        "&users[2][lastname]=" + URLEncoder.encode("usr", "UTF-8") +
        "&users[2][email]=" + URLEncoder.encode("test3@moodle.com", "UTF-8") +
        "&users[2][timezone]=" + URLEncoder.encode("Pacific/Port_Moresby", "UTF-8") + 
        
        "&users[3][username]=" + URLEncoder.encode(" cse1200101", "UTF-8") +
        "&users[3][password]=" + URLEncoder.encode("Test@123", "UTF-8") +
        "&users[3][firstname]=" + URLEncoder.encode("testfirstname2", "UTF-8") +
        "&users[3][lastname]=" + URLEncoder.encode("usr", "UTF-8") +
        "&users[3][email]=" + URLEncoder.encode("test4@moodle.com", "UTF-8") +
        "&users[3][timezone]=" + URLEncoder.encode("Pacific/Port_Moresby", "UTF-8") + 
        
        "&users[4][username]=" + URLEncoder.encode("cse130001001", "UTF-8") +
        "&users[4][password]=" + URLEncoder.encode("Test@123", "UTF-8") +
        "&users[4][firstname]=" + URLEncoder.encode("testfirstname2", "UTF-8") +
        "&users[4][lastname]=" + URLEncoder.encode("usr", "UTF-8") +
        "&users[4][email]=" + URLEncoder.encode("test5@moodle.com", "UTF-8") +
        "&users[4][timezone]=" + URLEncoder.encode("Pacific/Port_Moresby", "UTF-8") +  
        
        "&users[5][username]=" + URLEncoder.encode("pushkar", "UTF-8") +
        "&users[5][password]=" + URLEncoder.encode("Test@123", "UTF-8") +
        "&users[5][firstname]=" + URLEncoder.encode("testfirstname2", "UTF-8") +
        "&users[5][lastname]=" + URLEncoder.encode("usr", "UTF-8") +
        "&users[5][email]=" + URLEncoder.encode("test6@moodle.com", "UTF-8") +
        "&users[5][timezone]=" + URLEncoder.encode("Pacific/Port_Moresby", "UTF-8") + 
       
        "&users[6][username]=" + URLEncoder.encode("cseTest", "UTF-8") +
        "&users[6][password]=" + URLEncoder.encode("Test@123", "UTF-8") +
        "&users[6][firstname]=" + URLEncoder.encode("testfirstname2", "UTF-8") +
        "&users[6][lastname]=" + URLEncoder.encode("usr", "UTF-8") +
        "&users[6][email]=" + URLEncoder.encode("test7@moodle.com", "UTF-8") +
        "&users[6][timezone]=" + URLEncoder.encode("Pacific/Port_Moresby", "UTF-8");
        
        /*		"&courses[0][fullname]=" + URLEncoder.encode("TestCourse FullName", "UTF-8")+
		"&courses[0][shortname]="+ URLEncoder.encode("TS2344", "UTF-8")+
		"&courses[0][categoryid]="+ URLEncoder.encode("2", "UTF-8");
        "courses[0][maxbytes]= "+ URLEncoder.encode("testusername3", "UTF-8");
       */
/// REST CALL

        // Send request
        String serverurl = domainName + "/webservice/rest/server.php" + "?wstoken=" + token + "&wsfunction=" + functionName;
        HttpURLConnection con = (HttpURLConnection) new URL(serverurl).openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type",
           "application/x-www-form-urlencoded");
        con.setRequestProperty("Content-Language", "en-US");
        con.setDoOutput(true);
        con.setUseCaches (false);
        con.setDoInput(true);
        DataOutputStream wr = new DataOutputStream (
                  con.getOutputStream ());
        wr.writeBytes (urlParameters);
        wr.flush ();
        wr.close ();

        //Get Response
        InputStream is =con.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder response = new StringBuilder();
        while((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }

//        JsonReader response1 = Json.createReader(is);
//        JsonObject jsonObject = response1.readObject();
//        System.out.println("json response is :\n"+jsonObject.toString());
//        
        rd.close();
        System.out.println("String response is "+response.toString());
      
    }
}