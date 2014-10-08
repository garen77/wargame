package com.wg.ui.jsf;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.el.ELException;
import javax.el.VariableMapper;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;

import org.apache.log4j.Logger;


import com.sun.facelets.FaceletContext;
import com.sun.facelets.FaceletException;
import com.sun.facelets.TemplateClient;
import com.sun.facelets.el.VariableMapperWrapper;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagHandler;
import com.sun.facelets.tag.ui.DefineHandler;
import com.sun.facelets.tag.ui.ParamHandler;

public class Decorate extends TagHandler implements TemplateClient{

	protected static final String BASE_PATH = "/WEB-INF";
	protected static final String WEB_CONTENT_PATH = "";

	private final TagAttribute template;
	private  Map<String, DefineHandler> handlers;
	private ParamHandler[] params;
	static private Logger logging;

	protected Logger log(){
		if(logging == null)
			logging = Logger.getLogger(this.getClass());
		return logging;
	}

	public Decorate(TagConfig config) {
		super(config);
		log().info("Decorate - constructor start");
		template = this.getRequiredAttribute("template");
		this.handlers = new HashMap();
        Iterator itr = this.findNextByType(DefineHandler.class);
        DefineHandler d = null;
        while (itr.hasNext()) {
            d = (DefineHandler) itr.next();
            this.handlers.put(d.getName(), d);
        }
        List paramC = new ArrayList();
        itr = this.findNextByType(ParamHandler.class);
        while (itr.hasNext()) {
            paramC.add(itr.next());
        }
        if (paramC.size() > 0) {
            this.params = new ParamHandler[paramC.size()];
            for (int i = 0; i < this.params.length; i++) {
                this.params[i] = (ParamHandler) paramC.get(i);
            }
        } else {
            this.params = null;
        }        
        log().info("Decorate - constructor end");
		// TODO Auto-generated constructor stub
	}

	public void apply(FaceletContext ctx, UIComponent parent)
			throws IOException, FacesException, FaceletException, ELException {
		// TODO Auto-generated method stub
		VariableMapper orig = ctx.getVariableMapper();
		if (this.params != null) {
		    VariableMapper vm = new VariableMapperWrapper(orig);
		    ctx.setVariableMapper(vm);
		    for (int i = 0; i < this.params.length; i++) {
		        this.params[i].apply(ctx, parent);
		    }
		}
		log().info(" template : "+(String)template.getValue());
		TagAttribute defaultValue = this.getAttribute("theDefaultValue");
		
		String path = setPathInContext(ctx,template,defaultValue);
		log().info("path : "+path);
		ctx.setAttribute("thePath", path);
		ctx.pushClient(this);
		try {
			ctx.includeFacelet(parent, BASE_PATH+"/includes/commons/base.xhtml");
		} finally{
			ctx.popClient(this);
		}
	}

	public boolean apply(FaceletContext ctx, UIComponent parent, String name)
			throws IOException, FacesException, FaceletException, ELException {
		// TODO Auto-generated method stub
		if (name != null) {
		    DefineHandler handler = this.handlers.get(name);
		    if (handler != null) {
		    	handler.applyDefinition(ctx, parent);
		        return true;
		    } else {
		        return false;
		    }
		} else {
		    this.nextHandler.apply(ctx, parent);
		    return true;
		}
	}
	
	@SuppressWarnings("unchecked")
	private String fixPath(FaceletContext ctx, TagAttribute attr, String path){
		String newPath = "";
		String basePath = BASE_PATH;
		try{
			TagAttribute t = new TagAttribute(attr.getLocation(),attr.getNamespace(),
					attr.getLocalName(), attr.getQName(), path);
			newPath = basePath + t.getValue(ctx);
		}
		catch(NullPointerException npe)
		{
			newPath="/incul/ppp";	
		}
		return newPath;
	}

	private String setPathInContext(FaceletContext ctx, TagAttribute template,
			TagAttribute defaultValue) throws MalformedURLException {
		String sDefaultValue = null;
		if (defaultValue != null)
			sDefaultValue = defaultValue.getValue(ctx);
		String sTemplate = template.getValue();

		sDefaultValue="/includes/commons/home.xhtml";
		ctx.setAttribute("theDefaultValue", sDefaultValue);

		if (sTemplate != null) {
			ctx.setAttribute("theTemplate", sTemplate);
			String path = fixPath(ctx,template,(String)template.getValue());
			if(!pathExists(ctx, path))
				path = fixPath(ctx,template,sDefaultValue);
			ctx.setAttribute("thePath", path);
			return path;
		}
		return fixPath(ctx,template,sDefaultValue);
	}
	public boolean pathExists(FaceletContext ctx, String path)
	throws MalformedURLException {
		try {
			URL url = ctx.getFacesContext().getExternalContext().getResource(path);
			return url!=null;
		} catch (MalformedURLException e) {
			throw e;
		}
	}

}
