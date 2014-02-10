package in.ac.iiti.moodlerestapi;
	 
	import javax.ws.rs.GET;
	import javax.ws.rs.Path;
	import javax.ws.rs.PathParam;
	import javax.ws.rs.core.Response;
	 
	@Path("/hi")
	public class HelloWorldService
	{
	  @GET
	  public Response getMessage()
	  {
	    String outMsg = "Hello harshit !\n";
	    return Response.status(200).entity(outMsg).build();
	  }
	}