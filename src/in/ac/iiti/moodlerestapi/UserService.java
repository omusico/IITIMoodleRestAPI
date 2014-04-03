package in.ac.iiti.moodlerestapi;
 
import in.ac.iiti.moodlerestapi.util.Commons;

import java.io.IOException;
import java.net.URLEncoder;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 
@Path("/users")
public class UserService
{ 
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/me")
  public JsonArray getCallingUserDetails(@QueryParam("wstoken")String wstoken) throws IOException
	  {
	       JsonObject jsonObject = null;
	       try{
	           jsonObject= Commons.getJsonObject("POST",null, wstoken,"core_webservice_get_site_info");
	        } 
	        catch(javax.ws.rs.ProcessingException connectException){
	       	 System.out.println("log javax.ws.rs.ProcessingException thrown in Common.callWebservice");
	       	//TODO redirect to guest page
	       }
	       int userid = jsonObject.getInt("userid");
	       if(userid!=0){
	    	   return getUserDetailbyId(wstoken,userid);
	       }
	       
	       return null;
	  }	   
 	
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{userid}")
  public JsonArray getUserDetailbyId(@QueryParam("wstoken")String wstoken,
		                             @PathParam("userid")int userid) throws IOException
	  {
	       JsonArray jsonArray=null;
	       JsonObject jsonObject = null;
	       
	       String urlParams = "field="+URLEncoder.encode("id","UTF-8")+
	                           "&values[0]="+URLEncoder.encode(userid+"","UTF-8");
	       try{
	           jsonArray= Commons.getJsonArray("POST",urlParams, wstoken,"core_user_get_users_by_field");
	        } 
	        catch(javax.ws.rs.ProcessingException connectException){
	       	System.out.println("log javax.ws.rs.ProcessingException thrown in Common.callWebservice");
	       	//TODO redirect to guest page
	       }
	       
	       return jsonArray;
	  }
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{userid}/courses")
  public JsonArray getUserCoursesbyId(@QueryParam("wstoken")String wstoken,
		                             @PathParam("userid")int userid) throws IOException
	  {
	       JsonArray jsonArray=null;
	       JsonObject jsonObject = null;
	       String urlParams = "userid="+URLEncoder.encode(userid+"","UTF-8"); 
	       try{
	           jsonArray= Commons.getJsonArray("GET",urlParams, wstoken,"core_enrol_get_users_courses");
	          } 
	        catch(javax.ws.rs.ProcessingException connectException){
	       	System.out.println("log javax.ws.rs.ProcessingException thrown in Common.callWebservice");
	       	//TODO redirect to guest page
	       }
	       
	       return jsonArray;
	  }
}