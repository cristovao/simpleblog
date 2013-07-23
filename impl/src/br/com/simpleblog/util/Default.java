package br.com.simpleblog.util;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

public enum Default {

	DECORATOR("decorator.jsp"),
	MAIN("main.jsp", "/main"),
	POST("post.jsp", "/post"),
	ERROR("404.jsp", "/error");
	
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
		
		String requestURI = null;
		if (request.getRequestURI().startsWith("/")) {
			requestURI = (String)request.getRequestURI().replaceFirst("/", "");
		} else {
			requestURI = (String)request.getRequestURI();
		}
		
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
			if (local != DECORATOR && local != ERROR && local.isPath(requestURI)) {
				result = local;
				break;
			}
		}
		
		String[] split = requestURI.replaceFirst("/", "").split("/");
		if (result == null && split.length < 2 && !split[0].equals("default")) {
			File realFolder = new File(request.getServletContext().getRealPath("/"+split[0]));
			
			if (realFolder.exists()) {
				result = MAIN;
			}
		}
		
		return result;
	}
}
