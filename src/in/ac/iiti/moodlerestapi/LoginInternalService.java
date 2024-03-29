package in.ac.iiti.moodlerestapi;
 
import in.ac.iiti.moodlerestapi.util.AppConfigProperty;

import java.util.Properties;

import javax.json.JsonObject;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
 
public class LoginInternalService
{
  private static Properties propertyInstance = AppConfigProperty.getPropertyInstance();
  
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public JsonObject getToken(@FormParam(value = "username") String username,
		                     @FormParam(value = "password") String password)
  throws java.net.ConnectException{
	   
       String moodleServerUrl = propertyInstance.getProperty("moodleServerUrl");
       String moodleWebServiceShortname = "alanturing"; //TODO put it at a central location
       
       
       JsonObject response = null; 
       try{
           response = ClientBuilder.newClient()
        		                    .target(moodleServerUrl)
    		                        .path("/login/token.php")
    		                        .queryParam("username", username)
    		                        .queryParam("password", password)
    		                        .queryParam("service", moodleWebServiceShortname)
    		                        .request()
    		                        .get(JsonObject.class);
       }catch(javax.ws.rs.ProcessingException e){
    	   throw new java.net.ConnectException();
       }
       
       return response;
  }
  
  
  
}