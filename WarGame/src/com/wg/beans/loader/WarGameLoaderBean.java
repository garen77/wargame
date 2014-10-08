package com.wg.beans.loader;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wg.beans.BaseBean;
import com.wg.beans.state.LoginState;

@Service(value = WarGameLoaderBean.BEAN_NAME)
@Scope(value = "session")
public class WarGameLoaderBean extends BaseBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6912038048020271313L;

	public static final String BEAN_NAME= "warGameLoaderBean";
	
	
	
	private int idCurrentUser;
	
	@Override
	public void initActivity() {
		
		if(viewState == null)
		{
			viewState = new LoginState();
		}
			
	}

	
	
	public String back()
	{
		return "back";
	}
	
	public String start()
	{
		return "";
	}



	public int getIdCurrentUser() {
		return idCurrentUser;
	}



	public void setIdCurrentUser(int idCurrentUser) {
		this.idCurrentUser = idCurrentUser;
	}


}
