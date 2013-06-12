package br.com.simpleblog.taglib;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class DateTag extends BodyTagSupport {

	private String var;
	private String format;

	@Override
	public int doStartTag() throws JspException {
		
		Date date = (Date)pageContext.getAttribute(DateTag.class.getSimpleName());
		
		if (date != null && getFormat() != null && !getFormat().isEmpty() 
				&& getVar() != null && !getVar().isEmpty()) {
			DateFormat format = new SimpleDateFormat(getFormat());
			
			pageContext.setAttribute(getVar(), format.format(date));
			
			return EVAL_BODY_INCLUDE;
		}

		return SKIP_BODY;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getVar() {
		return var;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getFormat() {
		return format;
	}

}
