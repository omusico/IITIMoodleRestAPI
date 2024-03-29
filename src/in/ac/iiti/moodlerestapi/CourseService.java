package in.ac.iiti.moodlerestapi;
 
import java.io.IOException;
import java.net.URLEncoder;

import in.ac.iiti.moodlerestapi.util.Commons;

import javax.json.JsonArray;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
 
@Path("/courses")
public class CourseService
{
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public JsonArray getCourses(@QueryParam("wstoken")String wstoken) throws IOException
  {
	   
       JsonArray jsonArray=null;
       
       try{
           jsonArray= Commons.getJsonArray("GET",null, wstoken,"core_course_get_courses");
           
        } 
        catch(javax.ws.rs.ProcessingException connectException){
       	System.out.println("log javax.ws.rs.ProcessingException thrown in Common.callWebservice");
       	//TODO redirect to guest page
       }
       
       return jsonArray;
  }
  
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public JsonArray createCourses(@QueryParam("urlParams")String urlParams,
		                                @QueryParam("wstoken") String wstoken) throws IOException{
	  JsonArray jsonArray=null;
      
      try{
          jsonArray= Commons.getJsonArray("POST",urlParams, wstoken,"core_course_create_courses");
         } 
       catch(javax.ws.rs.ProcessingException connectException){
      	System.out.println("log javax.ws.rs.ProcessingException thrown in Common.callWebservice");
      	//TODO redirect to guest page
      }
      
      return jsonArray;
  }
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{courseId}/contents")
  public JsonArray getCourseContents(@PathParam("courseId")int courseId,
                                       @QueryParam("wstoken") String wstoken) throws IOException{
		JsonArray jsonArray=null;
		String urlParams = "courseid="+URLEncoder.encode(courseId+"","UTF-8");
		
		try{
		jsonArray= Commons.getJsonArray("POST",urlParams, wstoken,"core_course_get_contents");
		} 
		catch(javax.ws.rs.ProcessingException connectException){
		System.out.println(" log javax.ws.rs.ProcessingException thrown in Common.callWebservice");
		//TODO redirect to guest page
		}
		
		return jsonArray;
		}
		 
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{courseId}/participants")
  public JsonArray getCourseParticipants(@PathParam("courseId")int courseId,
                                       @QueryParam("wstoken") String wstoken) throws IOException{
		JsonArray jsonArray=null;
		String urlParams = "courseid="+URLEncoder.encode(courseId+"","UTF-8");
		try{
		jsonArray= Commons.getJsonArray("POST",urlParams, wstoken,"core_enrol_get_enrolled_users");
		} 
		catch(javax.ws.rs.ProcessingException connectException){
		System.out.println("log javax.ws.rs.ProcessingException thrown in Common.callWebservice");
		//TODO redirect to guest page
		}
		
		return jsonArray;
	}
  
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{courseId}/forums")
  public JsonArray getForumsByCourseId(@PathParam("courseId")int courseId,
                                   @QueryParam("wstoken") String wstoken) throws IOException{
	  
		JsonArray jsonArray=null;
		
		String urlParams = null;
    	urlParams="courseids[0]="+URLEncoder.encode(courseId+"","UTF-8");
        
	       
		try{
		    jsonArray= Commons.getJsonArray("POST",urlParams, wstoken,"mod_forum_get_forums_by_courses");
	
		} 
		catch(javax.ws.rs.ProcessingException connectException){
		    System.out.println("log javax.ws.rs.ProcessingException thrown in Common.callWebservice");
		}
		
		return jsonArray;
		}
}