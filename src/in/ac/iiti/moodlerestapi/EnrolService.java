package in.ac.iiti.moodlerestapi;

import in.ac.iiti.moodlerestapi.util.Commons;

import java.io.IOException;

import javax.json.JsonObject;
import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/enrol")
public class EnrolService {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void enrolUsers(@QueryParam("urlParams")String urlParams,
			                                @QueryParam("wstoken") String wstoken) throws IOException{
	  try{
          JsonObject jsonObject = Commons.getJsonObject("POST",urlParams, wstoken,"enrol_manual_enrol_users");
          if(jsonObject!=null){
        	  //System.out.println("Enrol info \n "+jsonObject.toString());
          }
         } 
	   catch(javax.json.stream.JsonParsingException parsingException){
		   //do nothing as this exception would occur in correct flow
	   }
       catch(javax.ws.rs.ProcessingException connectException){
      	System.out.println("log javax.ws.rs.ProcessingException thrown in Common.callWebservice");
       }
  }
}
