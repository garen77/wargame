package com.wg.beans;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.icesoft.faces.context.effects.JavascriptContext;
import com.wg.beans.loader.WarGameLoaderBean;
import com.wg.beans.state.ViewState;
import com.wg.services.factory.ServicesFactory;
import com.wg.services.spi.LoaderService;
import com.wg.spring.beans.SpringBeans;

public abstract class BaseBean implements Serializable{

	protected static final ServicesFactory sf = new ServicesFactory();

	private LoaderService loaderService;

	
	private WarGameLoaderBean warGameLoaderBean;

	protected ViewState viewState;

	private List<String> errorMessages = new ArrayList<String>();
	
	public LoaderService getLoaderService()
	{
		if(loaderService == null)
		{
			loaderService = (LoaderService) sf.getService(LoaderService.SERVICE_NAME);
		}
		return loaderService;
	}
	
	public WarGameLoaderBean getWarGameLoaderBean()
	{
		if(warGameLoaderBean == null)
		{
			warGameLoaderBean = (WarGameLoaderBean)SpringBeans.getBean(WarGameLoaderBean.BEAN_NAME);
		}
		return warGameLoaderBean;
	}
	
	@PostConstruct
	public void init()
	{
		FacesContext fc = FacesContext.getCurrentInstance();
		JavascriptContext.includeLib("/skins/js/scripts.js", fc);
		JavascriptContext.includeLib("/skins/jQueryLib/jquery-1.7.1.js", fc);
		initActivity();
	}
	
	public abstract void initActivity();

	public ViewState getViewState() {
		return viewState;
	}

	public void setViewState(ViewState viewState) {
		this.viewState = viewState;
	}

	public String back()
	{
		return "back";
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}
}
