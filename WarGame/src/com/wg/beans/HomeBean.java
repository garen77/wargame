package com.wg.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wg.beans.state.home.StartState;

@Service(value = HomeBean.BEAN_NAME)
@Scope(value = "request")
public class HomeBean extends BaseBean {

	public static final String BEAN_NAME= "homeBean";
	
	@Override
	public void initActivity() {
		
		/*FacesContext fc = FacesContext.getCurrentInstance();
		JavascriptContext.includeLib("/skins/backbone/json2.js", fc);
		JavascriptContext.includeLib("/skins/backbone/Underscore.js", fc);
		JavascriptContext.includeLib("/skins/backbone/UnderscoreForJsf.js", fc);
		JavascriptContext.includeLib("/skins/backbone/Backbone.js", fc);
		JavascriptContext.includeLib("/skins/js/home/Model.js", fc);
		JavascriptContext.includeLib("/skins/js/home/View.js", fc);*/
		viewState = new StartState();
	}

	public String play()
	{
		return "playArea";
	}

	public String play3D()
	{
		return "playArea3D";
	}
	
	public String playThree()
	{
		return "playAreaThree";
	}
}
