package com.ibm.in.sagaverm;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ibm.in.sagaverm.kg.KGCreate;


@Path("/")
public class KGCreationService {
	//static KGCreate kgCreate = new KGCreate();
	
	@POST
	@Path("/kgcs")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response kgCreatationREST(InputStream incomingData){
		StringBuilder stringBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		//KGCreate kgCreate = new KGCreate();
		System.out.println("Data Received: " + stringBuilder.toString());
		System.out.println("Result:" + KGCreate.getResult(stringBuilder.toString()));
		// return HTTP response 200 in case of success
		return Response.status(200).entity(KGCreate.getResult(stringBuilder.toString()).toString()).build();
	}
 
	@GET
	@Path("/verify")
	@Produces(MediaType.TEXT_PLAIN)
	public Response verifyRESTService(InputStream incomingData) {
		String result = "Service Successfully started..";
 
		// return HTTP response 200 in case of success
		return Response.status(200).entity(result).build();
	}
 
}
