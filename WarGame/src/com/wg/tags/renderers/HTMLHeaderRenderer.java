package com.wg.tags.renderers;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.http.HttpServletRequest;



import com.sun.faces.renderkit.html_basic.HtmlBasicRenderer;
import com.wg.tags.Header;

public class HTMLHeaderRenderer extends HtmlBasicRenderer {

	private static final String crlf = System.getProperty("line.separator");
	
	public HTMLHeaderRenderer()
	{
	}

	@Override
	public void encodeEnd(FacesContext context, UIComponent component)
			throws IOException {
		// TODO Auto-generated method stub
		ResponseWriter writer = context.getResponseWriter();
	    writer.endElement("head");
	}

	@Override
	public boolean getRendersChildren() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void encodeBegin(FacesContext context, UIComponent component)
			throws IOException {
		// TODO Auto-generated method stub
		ResponseWriter writer = context.getResponseWriter();
		Header header = null;
		if(component instanceof Header)
		{
			header = (Header)component;
		}
		if(header != null)
		{
			writer.startElement("head", header);
			writer.writeAttribute("id", header.getId(), "id");
			
			if(header.getTitle() != null)
			{
				writer.startElement("title", component);
				writer.writeText(header.getTitle(), "This is the title");
				writer.endElement("title");
			}
			writer.write(crlf);
			String skinBase = ((HttpServletRequest)context.getCurrentInstance().getExternalContext().getRequest()).getContextPath();
			addCSSDecl(writer, header, skinBase+"/css/generalCSS.css");
			addJSDecl(writer, header, skinBase+"/js/scripts.js");
		}
		
		
	}

	private void addCSSDecl(ResponseWriter writer, Header header, String url) throws IOException
	{
		writer.startElement("link", header);
		writer.writeAttribute("rel", "stylesheet", "rel");
	    writer.writeAttribute("type", "text/css", "type");
	    writer.writeAttribute("href", url, "href");
	    writer.endElement("link");
	    writer.write(crlf);
	}
	
	private void addJSDecl(ResponseWriter writer, Header header, String url) throws IOException
	{
	    writer.startElement("script", header);
	    writer.writeAttribute("type", "text/javascript", "type");
	    writer.writeAttribute("src", url, "src");
	    writer.endElement("script");
	    writer.write(crlf);
	}
}
