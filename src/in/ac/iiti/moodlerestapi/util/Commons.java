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
import java.util.Properties;

import javax.ws.rs.client.Invocation.Builder;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class Commons {
	
//	private static PropertyHandler propertyInstance= new PropertyHandler();
	private static Properties propertyInstance = AppConfigProperty.getPropertyInstance(); 
	
	public static JsonObject callWebService(String httpMethod,String urlParams, String token,String functionName)
			                                                                throws IOException,javax.ws.rs.ProcessingException{
	    String moodleServerUrl = propertyInstance.getProperty("moodleServerUrl");//"http://localhost/testmoodle";
	
		if(urlParams==null){
			urlParams="moodlewsrestformat=json";
		}
		else{
	        urlParams = "moodlewsrestformat=json&"+urlParams;
		}
		JsonObject response=null;
    
	    WebTarget webTarget = ClientBuilder.newClient()
              .target(moodleServerUrl)
              .path("/webservice/rest/server.php?wstoken=" + token + "&wsfunction=" + functionName);
              //.queryParam("wstoken", token)
              //.queryParam("wsfunction",functionName )
         //     .request();
        
	    if(httpMethod==null || httpMethod.equalsIgnoreCase("GET")){
	    	 System.out.println("Sending get request");
	    	Response response1 = webTarget.queryParam("moodlewsrestformat", "json").request(MediaType.APPLICATION_JSON).get();
	    	System.out.println("response of get request is \n"+response1.toString());
	    	
	    }
	    else if(httpMethod.equalsIgnoreCase("POST")){
	    	System.out.println("Sending post request");
	    	response = webTarget.request().post(Entity.entity(urlParams, MediaType.APPLICATION_FORM_URLENCODED_TYPE),JsonObject.class);
	    }
	    System.out.println("Request sent");
	    System.out.println("response is "+ response.toString());
	  
	    return response;
	}
		
	
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
        System.out.println("init response is :\n"+ responseBuilder.toString());        
        JsonReader jsonReader =Json.createReader(new StringReader(responseBuilder.toString()));
        JsonObject jsonResponse = jsonReader.readObject();
        System.out.println("response is :\n"+jsonResponse.toString());
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
        JsonArray jsonResponse = jsonReader.readArray();
        System.out.println("response is :\n"+jsonResponse.toString());
        rd.close();
        
        return jsonResponse;
	}

	
}
