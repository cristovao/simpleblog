package br.com.simpleblog.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class PreviewTag extends BodyTagSupport {

	@Override
	public int doEndTag() throws JspException {

		pageContext.getRequest().setAttribute(PreviewTag.class.getSimpleName(),
				getContent());

		return super.doEndTag();
	}

	private String getContent() {

		String body = bodyContent.getString();
		
		if (body == null) {
			body = "";
		}

		return body;
	}
}
