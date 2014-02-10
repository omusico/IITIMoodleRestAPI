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
 
@Path("/login")
public class LoginService
{
  private static Properties propertyInstance = AppConfigProperty.getPropertyInstance();
  
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public JsonObject getToken(@FormParam(value = "username") String username,
		                     @FormParam(value = "password") String password)
  {
	   
       String moodleServerUrl =propertyInstance.getProperty("moodleServerUrl");
       System.out.println("moodleServerUtl is : "+ moodleServerUrl);
       String moodleWebServiceShortname = "alanturing"; //TODO put it at a central location
       
       JsonObject response = ClientBuilder.newClient()
                                    .target(moodleServerUrl)
    		                        .path("/login/token.php")
    		                        .queryParam("username", username)
    		                        .queryParam("password", password)
    		                        .queryParam("service", moodleWebServiceShortname)
    		                        .request()
    		                        .get(JsonObject.class);
       
       return response;
  }
  
}