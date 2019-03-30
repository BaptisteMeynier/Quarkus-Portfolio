package org.acme.quarkus.portfolio.webservice.rest;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HelloEndpoint {

    @GET
    public String doGet() {
        return "Hello";
    }


}
