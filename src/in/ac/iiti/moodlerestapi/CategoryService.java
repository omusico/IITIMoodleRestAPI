package in.ac.iiti.moodlerestapi;
 
import java.io.IOException;

import in.ac.iiti.moodlerestapi.util.Commons;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.servlet.http.HttpSession;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
 
@Path("/categories")
public class CategoryService
{
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public JsonArray getCategories(@CookieParam("wstoken")String wstoken) throws IOException
  {
	   
       JsonArray jsonArray=null;
       
       try{
           jsonArray= Commons.getJsonArray("GET",null, wstoken,"core_course_get_categories");
           System.out.println("courses are \n "+ jsonArray.toString());
        } 
        catch(javax.ws.rs.ProcessingException connectException){
       	System.out.println("javax.ws.rs.ProcessingException thrown in Common.callWebservice");
       	//TODO redirect to guest page
       }
       
       return jsonArray;
  }
  
}