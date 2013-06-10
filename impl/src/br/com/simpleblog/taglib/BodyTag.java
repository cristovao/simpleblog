package br.com.simpleblog.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class BodyTag extends TagSupport {
	
	@Override
	public int doEndTag() throws JspException {
		
		String body = (String)pageContext.getRequest().getAttribute(BodyTag.class.getSimpleName());
		
		if (body == null) {
			body = "";
		}
		
		try {
			pageContext.getOut().print(body.trim());
		} catch (IOException e) {
			throw new JspException(e);
		}
		
		return EVAL_PAGE;
	}

}
