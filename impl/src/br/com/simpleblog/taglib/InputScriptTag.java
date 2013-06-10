package br.com.simpleblog.taglib;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class InputScriptTag extends BodyTagSupport {

	private String src;
	private String type;
	
	@Override
	public int doEndTag() throws JspException {
		List<String> typeContent = (List<String>)pageContext.getRequest().getAttribute(getType());
		
		if (typeContent == null) {
			typeContent = new ArrayList<String>();
			pageContext.getRequest().setAttribute(getType(), typeContent);
		}
		
		if (getSrc() != null && !getSrc().isEmpty()) {
			typeContent.add(getSrc());
		} else if (!getBodyContent().getString().isEmpty()) {
			typeContent.add(getBodyContent().getString());
		}
		
		return EVAL_PAGE;
	}
	
	public String getSrc() {
		return src;
	}
	
	public void setSrc(String src) {
		this.src = src;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
