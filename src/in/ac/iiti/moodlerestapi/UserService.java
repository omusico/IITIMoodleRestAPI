package in.ac.iiti.moodlerestapi;
 
import in.ac.iiti.moodlerestapi.util.Commons;

import java.io.IOException;
import java.net.URLEncoder;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 
@Path("/users")
public class UserService
{ 
  //me 
 	
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{userid}")
  public JsonArray getUserDetailbyId(@CookieParam("wstoken")String wstoken,
		                             @PathParam("userid")int userid) throws IOException
	  {
	       JsonArray jsonArray=null;
	       JsonObject jsonObject = null;
	       
	       String urlParams = "field="+URLEncoder.encode("id","UTF-8")+
	                           "&values[0]="+URLEncoder.encode(userid+"","UTF-8");
	       try{
	           jsonArray= Commons.getJsonArray("POST",urlParams, wstoken,"core_user_get_users_by_field");
	           jsonObject = jsonArray.getJsonObject(0);
	           System.out.println("users are \n "+ jsonArray.toString());
	        } 
	        catch(javax.ws.rs.ProcessingException connectException){
	       	System.out.println("javax.ws.rs.ProcessingException thrown in Common.callWebservice");
	       	//TODO redirect to guest page
	       }
	       
	       return jsonArray;
	  }
  
	
}