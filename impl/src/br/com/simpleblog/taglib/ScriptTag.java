package br.com.simpleblog.taglib;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class ScriptTag extends BodyTagSupport {

	private String var;
	private String type;
	private Queue<String> printQueue;
	
	@Override
	public int doStartTag() throws JspException {
		List<String> typeList = (List<String>)pageContext.getRequest().getAttribute(getType());
		
		if (typeList != null) {
			printQueue = new LinkedList<String>();
			
			for(String type : typeList) {
				printQueue.add(type);
			}
			
			pageContext.setAttribute(getVar(), printQueue.poll());
			
			return EVAL_BODY_INCLUDE;
		}
		
		return SKIP_BODY;
	}
	
	@Override
	public int doAfterBody() throws JspException {
		
		if (printQueue != null && !printQueue.isEmpty()) {
			pageContext.setAttribute(getVar(), printQueue.poll());
			return EVAL_BODY_AGAIN;
		}
		
		return SKIP_BODY;
	}
	
	@Override
	public int doEndTag() throws JspException {
		printQueue = null;
		return super.doEndTag();
	}
	
	public String getVar() {
		return var;
	}
	
	public String getType() {
		return type;
	}
	
	public void setVar(String var) {
		this.var = var;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
