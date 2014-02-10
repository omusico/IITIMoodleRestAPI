package in.ac.iiti.moodlerestapi;

import org.glassfish.jersey.server.ResourceConfig;

public class RestApplication extends ResourceConfig {
    public RestApplication(){
        packages("in.ac.iiti.moodlerestapi");
  }
 }