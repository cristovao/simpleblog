package br.com.simpleblog.taglib;

import javax.servlet.jsp.tagext.BodyTagSupport;

public class NextPageTag extends BodyTagSupport {

	private String var;
	
	public void setVar(String var) {
		this.var = var;
	}
	
	public String getVar() {
		return var;
	}
}
