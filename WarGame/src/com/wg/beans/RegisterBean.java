package com.wg.beans;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.icesoft.faces.context.effects.JavascriptContext;
import com.wg.beans.state.LoginState;
import com.wg.beans.state.register.RegisterFailureState;
import com.wg.beans.state.register.RegisterOkState;
import com.wg.beans.state.register.RegisterState;
import com.wg.dto.UserDTO;
import com.wg.result.RegisterResult;
import com.wg.services.api.IRegisterServices;

@Service(value = RegisterBean.BEAN_NAME)
@Scope(value = "request")
public class RegisterBean extends BaseBean {

	public static final String BEAN_NAME= "registerBean";
	/**
	 * 
	 */
	private static final long serialVersionUID = 5688262858996429268L;

	private String userName;
	
	private String password;
	
	private String mail;

	
	@Override
	public void initActivity() {
		
		/*FacesContext fc = FacesContext.getCurrentInstance();
		JavascriptContext.includeLib("/skins/js/register.js", fc);*/

		viewState = new RegisterState();

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String register()
	{
		IRegisterServices registerServices = (IRegisterServices)sf.getService(IRegisterServices.SERVICE_NAME);
		if(registerServices != null)
		{
			UserDTO userDto = new UserDTO();
			userDto.setUserName(userName);
			userDto.setMail(mail);
			userDto.setPassword(password);
			RegisterResult result = null;
			try
			{
				result = registerServices.register(userDto);
			}
			catch(Exception ex)
			{
				result = new RegisterResult();
				result.getMessages().add(ex.getMessage());
			}
			if(result != null && (result.getMessages() == null || result.getMessages().isEmpty()))
			{
				setViewState(new RegisterOkState());
			}
			else if(result == null || (result.getMessages() != null && !result.getMessages().isEmpty()))
			{
				setViewState(new RegisterFailureState());
				if(result != null && result.getMessages() != null && !result.getMessages().isEmpty())
				{
					getErrorMessages().addAll(result.getMessages());
				}
			}
		}
		return "";
	}


	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	

}
