package in.ac.iiti.moodlerestapi;
	 
	import javax.ws.rs.GET;
	import javax.ws.rs.Path;
	import javax.ws.rs.PathParam;
	import javax.ws.rs.core.Response;
	 
	@Path("/test")
	public class TestService
	{
	  @GET
	  public Response getMessage()
	  {
	    String outMsg = "It works";
	    return Response.status(200).entity(outMsg).build();
	  }
	}