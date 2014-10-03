package com.wg.services.spi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wg.assembler.UserAssembler;
import com.wg.assembler.UserFEAssembler;
import com.wg.dao.spi.UserDao;
import com.wg.dto.UserDTO;
import com.wg.dto.UserFrontEnd;
import com.wg.model.User;
import com.wg.services.api.IHomeServices;

@Service(value=IHomeServices.SERVICE_NAME)
public class HomeServices extends GenericService implements IHomeServices {

	@Autowired
	private UserDao userDao;

	@Transactional
	@Override
	public UserFrontEnd loadUser(UserDTO dto) {
		User user = userDao.findByKey(dto.getIdUser());
		UserFEAssembler assembler = new UserFEAssembler();
		UserFrontEnd userFrontEnd = new UserFrontEnd();
		assembler.toDto(user, userFrontEnd);
		return userFrontEnd;
	}

}
