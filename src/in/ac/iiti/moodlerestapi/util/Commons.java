package in.ac.iiti.moodlerestapi.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.ConnectException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;

import javax.ws.rs.client.Invocation.Builder;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonException;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class Commons {
	
//	private static PropertyHandler propertyInstance= new PropertyHandler();
	private static Properties propertyInstance = AppConfigProperty.getPropertyInstance(); 
	
	
    /**
     * 
     * @param httpMethod
     * @param urlParams
     * @param token
     * @param functionName
     * @return
     * @throws IOException
     */
	public static JsonObject getJsonObject(String httpMethod,String urlParams, String token,String functionName) throws IOException{
        
		
		String moodleServerUrl = propertyInstance.getProperty("moodleServerUrl");
		
		if(urlParams==null|| urlParams.equals("")){
			urlParams="moodlewsrestformat=json";
		}
		else{
	        urlParams = urlParams+ "&moodlewsrestformat=json";
		}
	   
	    // Send request
        String serverurl = moodleServerUrl + "/webservice/rest/server.php" + "?wstoken=" + token + "&wsfunction=" + functionName;
        HttpURLConnection con = (HttpURLConnection) new URL(serverurl).openConnection();
        con.setRequestMethod(httpMethod.toUpperCase());
        con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
//        con.setRequestProperty("Content-Language", "en-US");
        con.setDoOutput(true);
        con.setUseCaches (false);
        con.setDoInput(true);
        DataOutputStream wr = new DataOutputStream (
                  con.getOutputStream ());
        wr.writeBytes (urlParams);
        wr.flush ();
        wr.close ();

        //Get Response
        InputStream is =con.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder responseBuilder = new StringBuilder();
        while((line = rd.readLine()) != null) {
            responseBuilder.append(line);
            responseBuilder.append('\r');
        }
        //System.out.println("init response is :\n"+ responseBuilder.toString());        
        JsonReader jsonReader =Json.createReader(new StringReader(responseBuilder.toString()));
        JsonObject jsonResponse = jsonReader.readObject();
        //System.out.println("response is :\n"+jsonResponse.toString());
        rd.close();
        
        return jsonResponse;
	}

    public static JsonArray getJsonArray(String httpMethod,String urlParams, String token,String functionName) throws IOException{
        
		
		String moodleServerUrl = propertyInstance.getProperty("moodleServerUrl");
		
		if(urlParams==null|| urlParams.equals("")){
			urlParams="moodlewsrestformat=json";
		}
		else{
	        urlParams = urlParams+"&moodlewsrestformat=json";
		}
	   
	    // Send request
        String serverurl = moodleServerUrl + "/webservice/rest/server.php" + "?wstoken=" + token + "&wsfunction=" + functionName;
        HttpURLConnection con = (HttpURLConnection) new URL(serverurl).openConnection();
        con.setRequestMethod(httpMethod.toUpperCase());
        con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
//        con.setRequestProperty("Content-Language", "en-US");
        con.setDoOutput(true);
        con.setUseCaches (false);
        con.setDoInput(true);
        DataOutputStream wr = new DataOutputStream (
                  con.getOutputStream ());
        wr.writeBytes (urlParams);
        wr.flush ();
        wr.close ();

        //Get Response
        InputStream is =con.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder responseBuilder = new StringBuilder();
        while((line = rd.readLine()) != null) {
            responseBuilder.append(line);
            responseBuilder.append('\r');
        }
        
        JsonReader jsonReader =Json.createReader(new StringReader(responseBuilder.toString()));
        JsonArray jsonResponse = null;
        try{
          jsonResponse = jsonReader.readArray();
        }catch(JsonException e){
        	JsonReader jsonReader1 =Json.createReader(new StringReader(responseBuilder.toString()));
        	JsonObject jsonObject = jsonReader1.readObject();
        	jsonResponse = Json.createArrayBuilder().add(jsonObject).build();
        }
        rd.close();
        
        return jsonResponse;
	}

}
