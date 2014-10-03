package com.wg.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import com.wg.dto.SimpleResult;
import com.wg.dto.UserDTO;
import com.wg.dto.UserFrontEnd;
import com.wg.rest.services.api.ILoadUserServices;
import com.wg.services.api.IHomeServices;
import com.wg.services.api.ILoaderServices;

@Path("/WGRest")
public class WGRest extends RestResource {

	@Path("/users/{idUser}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public UserFrontEnd viewUser(@PathParam("idUser") int idUser) throws JAXBException
	{
		ILoaderServices services = (ILoaderServices)sf.getService(ILoaderServices.SERVICE_NAME);
		IHomeServices homeServices = (IHomeServices)sf.getService(IHomeServices.SERVICE_NAME);
//		return services.loadUser(idUser);
		UserDTO dto = new UserDTO();
		dto.setIdUser(idUser);
		return homeServices.loadUser(dto);
	}

	@Path("/controlMail")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public SimpleResult controlUserByNick(@QueryParam("mail") String mail) throws IllegalArgumentException, IllegalAccessException
	{
		ILoadUserServices services = (ILoadUserServices)sf.getService(ILoadUserServices.SERVICE_NAME);
		UserDTO dto = new UserDTO();
		dto.setMail(mail);
		return services.genericSearch(dto);
	}

	@Path("/controlUserName")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public SimpleResult controlUserByMail(@QueryParam("userName") String userName) throws IllegalArgumentException, IllegalAccessException
	{
		ILoadUserServices services = (ILoadUserServices)sf.getService(ILoadUserServices.SERVICE_NAME);
		UserDTO dto = new UserDTO();
		dto.setUserName(userName);
		return services.genericSearch(dto);
	}

}
