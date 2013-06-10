package br.com.simpleblog.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.simpleblog.util.Blog;

public abstract class HttpServlet extends javax.servlet.http.HttpServlet {
	
	@Override
	protected final void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.service((BlogHttpServletRequestWrapper)request, response);
	}
	
	protected void service(BlogHttpServletRequestWrapper request, HttpServletResponse response)
			throws ServletException, IOException {
		super.service(request, response);
	}
	
	@Override
	protected final void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet((BlogHttpServletRequestWrapper)req, resp);
	}
	
	protected void doGet(BlogHttpServletRequestWrapper req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
	}
	
	
	@Override
	protected final void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost((BlogHttpServletRequestWrapper)req, resp);
	}
	
	protected void doPost(BlogHttpServletRequestWrapper req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doPost(req, resp);
	}
	
}
