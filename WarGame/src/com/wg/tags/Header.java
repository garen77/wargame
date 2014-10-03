package com.wg.tags;

import javax.el.ValueExpression;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

public class Header extends UIOutput {

	public static final String COMPONENT_TYPE = ComponentConstants.COMPONENT_TYPE_CLASSPATH + "Header";
	public static final String COMPONENT_FAMILY = COMPONENT_TYPE;
	public static final String RENDERER_TYPE = ComponentConstants.RENDERER_TYPE_CLASSPATH + "HTMLHeaderRenderer";
	
	private String title = null;
	private Object[] state = null;
	
	public Header()
	{
		setId("head");
	}

	public String getTitle()
	{
		if(title != null)
		{
			return title;
		}
		ValueExpression ve  = getValueExpression("title");
		return (ve != null) ? (String)ve.getValue(getFacesContext().getELContext()) : null;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	@Override
	public String getFamily() {
		// TODO Auto-generated method stub
		return COMPONENT_FAMILY;
	}

	@Override
	public void restoreState(FacesContext context, Object state) {
		// TODO Auto-generated method stub
		this.state = (Object[])state;
		super.restoreState(context, this.state[0]);
		title = (String)this.state[1];
	}

	@Override
	public Object saveState(FacesContext context) {
		// TODO Auto-generated method stub
		if(state == null)
		{
			state = new Object[2];
		}
		state[0] = super.saveState(context);
		state[1] = title;
		return state;
	}
	
	
}
