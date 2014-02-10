package in.ac.iiti.moodlerestapi;

import in.ac.iiti.moodlerestapi.util.Commons;

import java.io.IOException;
import java.net.URLEncoder;

import javax.json.JsonArray;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 
@Path("/forums")
public class ForumService
{
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  public JsonArray getAllAccessibleForums(@CookieParam("wstoken")String wstoken) throws IOException
	  {       
	      return new CourseService().getForumsByCourseId("", wstoken);
	  }
	  
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  @Path("/{forumId}/discussions")
	  public JsonArray getForumDiscussions(@PathParam("forumId")String forumId,
	                                         @CookieParam("wstoken") String wstoken) throws IOException{
			JsonArray jsonArray=null;
			String urlParams = "forumids[0]="+URLEncoder.encode(forumId,"UTF-8");
			try{
			jsonArray= Commons.getJsonArray("POST",urlParams, wstoken,"mod_forum_get_forum_discussions");
			System.out.println("courses are \n "+ jsonArray.toString());
			} 
			catch(javax.ws.rs.ProcessingException connectException){
			System.out.println("javax.ws.rs.ProcessingException thrown in Common.callWebservice");
			//TODO redirect to guest page
			}
			
			return jsonArray;
		}
	  
}