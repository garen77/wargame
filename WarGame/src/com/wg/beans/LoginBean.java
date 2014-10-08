package com.wg.beans;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wg.beans.loader.WarGameLoaderBean;
import com.wg.beans.state.LoginState;
import com.wg.beans.state.register.RegisterState;
import com.wg.dto.UserDTO;

@Service(value = LoginBean.BEAN_NAME)
@Scope(value = "request")
public class LoginBean extends BaseBean {

	
	public static final String BEAN_NAME= "loginBean";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1864120842137006757L;

	private String userName;
	
	private String password;

	@Override
	public void initActivity() {
		
		getWarGameLoaderBean().viewState = new LoginState();

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
	
	public String login()
	{
		String res = "failure";
		boolean loginOk = false;
		UserDTO userDto = new UserDTO();
		userDto.setUserName(userName);
		userDto.setPassword(password);
		try {
			UserDTO result = getLoaderService().login(userDto);
			if(result != null && result.isFound())
			{
				getWarGameLoaderBean().setIdCurrentUser(result.getIdUser());
				loginOk = true;				
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(loginOk)
		{
			res = "successful";
		}
		return res;
	}

	public String register()
	{
		getWarGameLoaderBean().viewState = new RegisterState();
		return getWarGameLoaderBean().viewState.getView();
	}

}
