package br.com.simpleblog.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class TitleTag extends TagSupport {

	@Override
	public int doEndTag() throws JspException {

		String title = (String) pageContext.getRequest().getAttribute(
				TitleTag.class.getSimpleName());

		if (title == null) {
			title = "";
		}

		try {
			pageContext.getOut().print(title);
		} catch (IOException e) {
			throw new JspException(e);
		}

		return EVAL_PAGE;
	}
}
