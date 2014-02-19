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

*/
public class TestClass {
	
	public void testProperty(){
		
    	}
	
	    public static void main(String[] args) throws IOException {
	        System.out.println(new String("cs1000320000").matches("[a-z]{2,3}[0-9]{7,10}"));
	        System.out.println(new String("cs1000320000").replaceAll("[a-z]{2,3}",""));
	        
	    }
}