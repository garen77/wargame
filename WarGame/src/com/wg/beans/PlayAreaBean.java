package com.wg.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wg.beans.state.playArea.StartState;

@Service(value = PlayAreaBean.BEAN_NAME)
@Scope(value = "request")
public class PlayAreaBean extends BaseBean {

	public static final String BEAN_NAME= "playAreaBean";
	
	@Override
	public void initActivity() {

		viewState = new StartState();

	}

}
