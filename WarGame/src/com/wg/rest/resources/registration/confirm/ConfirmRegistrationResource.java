package com.wg.rest.resources.registration.confirm;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import com.wg.rest.resources.RestResource;
import com.wg.rest.services.api.IConfirmRegistrationServices;

@Path("/ConfirmRegistration")
public class ConfirmRegistrationResource extends RestResource{

	@Path("{idUser}")
	@GET
	@Produces({MediaType.TEXT_HTML})
	public String viewUser(@PathParam("idUser") long idUser) throws JAXBException
	{
		String form="<form name=\"confirmUser\" method=\"POST\" action=\"/WarGame/rest/ConfirmRegistration/confirmUser/"+idUser+"\">" +
				"<h2>Confirm Registration</h2><input type=\"submit\"></input></form>"; 
		return form;
	}

	@Path("/confirmUser/{idUser}")
	@POST
	@Produces({MediaType.APPLICATION_XML})
	public String confirmUser(@PathParam("idUser") long idUser) throws JAXBException
	{
		IConfirmRegistrationServices confirmServies = (IConfirmRegistrationServices)sf.getService(IConfirmRegistrationServices.SERVICE_NAME);
		
		return confirmServies.confirmUser(idUser);
	}

}
