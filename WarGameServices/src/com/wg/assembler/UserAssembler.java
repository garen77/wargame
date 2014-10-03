package com.wg.assembler;

import com.wg.dto.UserDTO;
import com.wg.model.User;

public class UserAssembler implements IAssembler<User, UserDTO> {

	@Override
	public void toDto(User model, UserDTO dto) {

		if(model != null && dto != null)
		{
			dto.setIdUser(model.getIdUser());
			dto.setMail(model.getMail());
			dto.setPassword(model.getPassword());
			dto.setUserName(model.getNick());
			dto.setVerified(model.getVerified());
		}
		
	}

	@Override
	public void fromDto(User model, UserDTO dto) {

		if(model != null && dto != null)
		{
			model.setIdUser(dto.getIdUser());
			model.setMail(dto.getMail());
			model.setNick(dto.getUserName());
			model.setPassword(dto.getPassword());
			model.setVerified(dto.getVerified());
		}

		
	}

}
