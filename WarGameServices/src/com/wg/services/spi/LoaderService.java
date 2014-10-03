package com.wg.services.spi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wg.assembler.UserAssembler;
import com.wg.dao.spi.CharacterDao;
import com.wg.dao.spi.UserDao;
import com.wg.dto.UserDTO;
import com.wg.model.BaseModel;
import com.wg.model.User;
import com.wg.services.api.ILoaderServices;
import com.wg.services.api.ILoginServices;

@Service(value=ILoaderServices.SERVICE_NAME)
@Transactional
public class LoaderService extends GenericService implements ILoaderServices,ILoginServices {

	

	@Autowired
	private CharacterDao characterDao;

	@Autowired
	private UserDao userDao;
	
	@Override
	public UserDTO login(UserDTO userDto) throws IllegalArgumentException, IllegalAccessException {
		UserAssembler assembler = new UserAssembler();
		User user = new User();
		assembler.fromDto(user, userDto);
		List<com.wg.model.User> lst = userDao.findByCriteria(user);
		if(lst != null && lst.size() ==1)
		{	
			userDto.setFound(true);
			userDto.setIdUser(lst.get(0).getIdUser());
		}
		return userDto;
	}

	@Override
	public UserDTO loadUser(long idUser) {
				
		User user = userDao.findByKey(idUser);
		UserDTO userDto = new UserDTO();
		if(user != null)
		{
			UserAssembler assembler = new UserAssembler();
			assembler.toDto(user, userDto);
		}
		return userDto;
	}
	
	
	
}
