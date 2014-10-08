package com.wg.rest.services.spi;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wg.assembler.UserAssembler;
import com.wg.dao.spi.UserDao;
import com.wg.dto.UserDTO;
import com.wg.model.User;
import com.wg.rest.services.api.IConfirmRegistrationServices;

@Service(value = IConfirmRegistrationServices.SERVICE_NAME)
@Transactional
public class ConfirmRegistrationServices extends RestServices<UserDTO> implements IConfirmRegistrationServices{

	@Autowired
	private UserDao userDao;
	
	@Override
	public String confirmUser(long idUser) throws JAXBException {
		User user = userDao.findByKey(idUser);
		if(user !=  null)
		{
			user.setVerified("1");
			userDao.update(user);
		}
		
		UserAssembler assembler = new UserAssembler();
		UserDTO userDto = new UserDTO();
		assembler.toDto(user, userDto);
		
		return marshallObject(userDto);
	}

	
}
