package br.com.simpleblog.taglib;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public class CheckScriptTag extends TagSupport {
	
	private String type;
	
	@SuppressWarnings("unchecked")
	@Override
	public int doStartTag() throws JspException {
		
		List<String> typeList = (List<String>)pageContext.getRequest().getAttribute(getType());
		
		if (typeList != null && !typeList.isEmpty()) {
			return EVAL_BODY_INCLUDE;
		}
		
		return SKIP_BODY;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

}
