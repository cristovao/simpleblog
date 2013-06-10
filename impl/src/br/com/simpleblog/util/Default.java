package br.com.simpleblog.util;

import javax.servlet.http.HttpServletRequest;

public enum Default {

	DECORATOR("decorator.jsp"),
	MAIN("main.jsp", "/main"),
	POST("post.jsp", "/post"),
	ERROR("404.jsp", "/error"),
	ABOUT("about.jsp", "/about");
	
	private String jsp;
	private String path;
	
	private Default(String jsp) {
		this.jsp = jsp;
	}
	
	private Default(String jsp, String path) {
		this.jsp = jsp;
		this.path = path;
	}
	
	public String getPath(HttpServletRequest request) {
		
		String requestURI = (String)request.getRequestURI();
		
		String[] uriArray = requestURI.split("/");
		
		if (uriArray.length > 0) {
			String path = "/"+uriArray[0]+"/"+jsp;
			
			if (request.getServletContext().getResourceAsStream(path) != null) {
				return path;
			}
		}
		
		return "/default/"+jsp;
	}
	
	public boolean isPath(String uri) {
		return uri.contains(path);
	}
	
	public static boolean isValid(HttpServletRequest request) {
		return get(request) != null;
	}
	
	public String getPath() {
		return path;
	}
	
	public static Default get(HttpServletRequest request) {

		String requestURI = request.getRequestURI();
		
		Default result = null;
		
		for (Default local : values()) {
			if (local != DECORATOR && local.isPath(requestURI)) {
				result = local;
				break;
			}
		}
		
		return result;
	}
}
