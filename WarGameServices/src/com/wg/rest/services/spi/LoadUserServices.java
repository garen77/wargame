package com.wg.rest.services.spi;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wg.assembler.UserAssembler;
import com.wg.dao.spi.UserDao;
import com.wg.dto.SimpleResult;
import com.wg.dto.UserDTO;
import com.wg.model.User;
import com.wg.rest.services.api.ILoadUserServices;

@Service(value = ILoadUserServices.SERVICE_NAME)
@Transactional
public class LoadUserServices extends RestServices<UserDTO> implements ILoadUserServices{

	@Autowired
	private UserDao userDao;
	
	@Override
	public String xmlStringUser(long idUser) throws JAXBException {
		
		User user = userDao.findByKey(idUser);
		
		UserAssembler assembler = new UserAssembler();
		UserDTO userDto = new UserDTO();
		assembler.toDto(user, userDto);
		return marshallObject(userDto);		
	}

	public SimpleResult genericSearch(UserDTO userDto) throws IllegalArgumentException, IllegalAccessException
	{
		UserAssembler assembler = new UserAssembler();
		User user = new User();
		assembler.fromDto(user, userDto);
		List<com.wg.model.User> lst = userDao.findByCriteria(user);
		SimpleResult result = new SimpleResult();
		if(lst != null && lst.size()>0)
		{
			result.setFound(true);
		}
		return result;
	}

	
}
