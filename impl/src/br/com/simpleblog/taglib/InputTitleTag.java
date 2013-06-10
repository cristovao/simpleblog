package br.com.simpleblog.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import br.com.simpleblog.util.Default;

public class InputTitleTag extends BodyTagSupport {

	private String title;
	private String var;

	@Override
	public int doStartTag() throws JspException {

		if (getVar() != null && !getVar().isEmpty()) {
			pageContext.setAttribute(getVar(), getContent());

			Default main = Default.get((HttpServletRequest) pageContext
					.getRequest());

			if (main != Default.MAIN)
				pageContext.getRequest().setAttribute(
						TitleTag.class.getSimpleName(), getContent());
		}

		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {

		if (getTitle() != null && !getTitle().isEmpty()) {
			pageContext.getRequest().setAttribute(
					InputTitleTag.class.getSimpleName(), getTitle());
		}

		return EVAL_PAGE;
	}

	private String getContent() {
		String body = (String) pageContext.getRequest().getAttribute(
				InputTitleTag.class.getSimpleName());

		if (body == null) {
			body = "";
		}

		return body;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}
}
