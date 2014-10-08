package com.wg.assembler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.wg.dto.UserFrontEnd;
import com.wg.model.Character;
import com.wg.model.Feature;
import com.wg.model.User;

public class UserFEAssembler implements IAssembler<User, UserFrontEnd> {

	@Override
	public void toDto(User model, UserFrontEnd dto) {
		if(model != null && dto != null)
		{
			dto.setIdUser(model.getIdUser());
			dto.setUserName(model.getNick());
			dto.setMail(model.getMail());
			if(model.getCharacters() != null && !model.getCharacters().isEmpty())
			{
				Character character = model.getCharacters().iterator().next();
				if(character != null && character.getId() > 0)
				{
					dto.setIdCharacter(character.getId());
					if(character.getFeatures() != null && !character.getFeatures().isEmpty())
					{
						List<Long> idFeats = new ArrayList<Long>();
						Iterator<Feature> it = character.getFeatures().iterator();
						while(it.hasNext())
						{
							Feature feat = it.next();
							if(feat != null && feat.getId() > 0)
							{
								idFeats.add(new Long(feat.getId()));
							}
						}
						dto.getIdFeatures().addAll(idFeats);
					}
				}
			}
			
		}
		
	}

	@Override
	public void fromDto(User model, UserFrontEnd dto) {
		// TODO Auto-generated method stub
		
	}

}
