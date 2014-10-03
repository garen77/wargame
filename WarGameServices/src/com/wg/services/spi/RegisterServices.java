package com.wg.services.spi;

import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wg.assembler.UserAssembler;
import com.wg.criteria.MailSenderCriteria;
import com.wg.dao.spi.CharacterDao;
import com.wg.dao.spi.FeatureDao;
import com.wg.dao.spi.UserDao;
import com.wg.dto.UserDTO;
import com.wg.model.Character;
import com.wg.model.Feature;
import com.wg.model.User;
import com.wg.result.MailSenderResult;
import com.wg.result.RegisterResult;
import com.wg.services.api.IMailSenderServices;
import com.wg.services.api.IRegisterServices;
import com.wg.services.factory.ServicesFactory;

@Service(value=IRegisterServices.SERVICE_NAME)
@Transactional
public class RegisterServices extends GenericService implements IRegisterServices {

	
	protected static final ServicesFactory sf = new ServicesFactory();
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private CharacterDao characterDao;
	
	@Autowired
	private FeatureDao featureDao;
	
	@Override
	public RegisterResult register(UserDTO userDto) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{		
		ResourceBundle rb = getResourceBundle(GENERIC_RESOURCES_BUNDLE);
		RegisterResult result = new RegisterResult();
		UserAssembler assembler = new UserAssembler();
		User user = new User();
		//mail control
		if(userDto.getMail() != null)
		{
			user.setMail(userDto.getMail().trim());
		}
		List<User> users = userDao.findByCriteria(user);
		if(users != null && users.size()>0)
		{
			StringBuilder keyMess = new StringBuilder(IRegisterServices.SERVICE_NAME).append(".err.")
					.append("mailAlreadyRegistered");
			result.getMessages().add(rb.getString(keyMess.toString()));
		}

		// username control
		user.setMail(null);
		if(userDto.getUserName() != null)
		{
			user.setNick(userDto.getUserName().trim());
		}
		users = userDao.findByCriteria(user);
		if(users != null && users.size()>0)
		{
			StringBuilder keyMess = new StringBuilder(IRegisterServices.SERVICE_NAME).append(".err.")
					.append("usernameAlreadyUsed");
			result.getMessages().add(rb.getString(keyMess.toString()));
		}

		if(result.getMessages().isEmpty())
		{
			assembler.fromDto(user, userDto);
			users = userDao.findByCriteria(user);

			user.setRegisterDate(new Date(new java.util.Date().getTime()));
			user.setVerified("0");
			Character baseCharacter = characterDao.findByKey(10);
			user.getCharacters().add(baseCharacter);
			try
			{
				userDao.save(user);
			}
			catch(HibernateException he)
			{
				result.getMessages().add(he.getMessage());
			}
			// sending mail
			MailSenderCriteria mailSenderCriteria = new MailSenderCriteria();
			mailSenderCriteria.setFromAddr("pippo@pelo.it");
			mailSenderCriteria.setToAddr(userDto.getMail());
			IMailSenderServices mailSenderServices = (IMailSenderServices)sf.getBean(IMailSenderServices.SERVICE_NAME);
			MailSenderResult mailSenderResult = mailSenderServices.sendMail(mailSenderCriteria);

			if((result.getMessages() == null || result.getMessages().isEmpty())
					&&
					(mailSenderResult == null || mailSenderResult.getMessages() == null
					|| mailSenderResult.getMessages().isEmpty()))
			{
				result.setRegisterOperation(true);
			}
		}
		return result;
	}

}
