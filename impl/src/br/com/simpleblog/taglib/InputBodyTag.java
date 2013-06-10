package br.com.simpleblog.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class InputBodyTag extends BodyTagSupport {

	private String var;

	@Override
	public int doStartTag() throws JspException {
		if (getVar() != null && !getVar().isEmpty()) {
			String content = getContent();

			pageContext.setAttribute(getVar(), content);

			return EVAL_BODY_INCLUDE;
		}
		
		return super.doStartTag();
	}
	
	private String getContent() {
		String body = (String) pageContext.getRequest().getAttribute(
				InputBodyTag.class.getSimpleName());

		if (body == null) {
			body = "";
		}

		return body;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getVar() {
		return var;
	}
}
