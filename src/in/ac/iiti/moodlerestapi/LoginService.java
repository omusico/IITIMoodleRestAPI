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
 
@Path("/login")
public class LoginService
{
  private static Properties propertyInstance = AppConfigProperty.getPropertyInstance();
  
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Response getToken(@FormParam(value = "username") String username,
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
           //give proper error code in error situation
           if(response.containsKey("error")){
        	   String error= response.getString("error");
        	   if(error.equalsIgnoreCase("The username was not found in the database")){
        		   return Response.status(401).entity("{}").type(MediaType.APPLICATION_JSON).build();
        	   }
        	   else{ 
        		   return Response.status(500).entity("{}").type(MediaType.APPLICATION_JSON).build();
        	   }
           }
           
       }catch(javax.ws.rs.ProcessingException e){
    	   throw new java.net.ConnectException();
       }
       
       return Response.status(200).entity(response).type(MediaType.APPLICATION_JSON).build();
  }
  
  
  
}