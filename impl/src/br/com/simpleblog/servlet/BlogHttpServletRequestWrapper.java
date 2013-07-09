package br.com.simpleblog.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import br.com.simpleblog.util.Blog;
import br.com.simpleblog.util.ContainerBlog;
import br.com.simpleblog.util.Default;

public class BlogHttpServletRequestWrapper extends HttpServletRequestWrapper {

	private static final String SIMPLE_REQUEST_URI = "SIMPLE_REQUEST_URI";
	
	public BlogHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		
		String requestURI = request.getRequestURI().replaceFirst("/", "");
		
		request.setAttribute(SIMPLE_REQUEST_URI, requestURI);
		
		String[] split = requestURI.split("/");
		
		if (split.length > 0) {
			Blog blog = this.getContainerBlog().get(split[0]);
			
			this.setAttribute(Blog.class.getSimpleName(), blog);
		}
	}
	
	@Override
	public String getRequestURI() {
		String requestURI = (String)this.getAttribute(SIMPLE_REQUEST_URI);
		
		if (requestURI == null) {
			requestURI = super.getRequestURI();
		}
		
		return requestURI;
	}
	
	public boolean hasBlog() {
		return this.getBlog() != null;
	}
	
	public Blog getBlog() {
		return (Blog)this.getAttribute(Blog.class.getSimpleName());
	}
	
	public ContainerBlog getContainerBlog() {
		ContainerBlog containerBlog = (ContainerBlog) getServletContext()
				.getAttribute(ContainerBlog.class.getSimpleName());

		if (containerBlog == null) {
			containerBlog = new ContainerBlog(getServletContext());

			getServletContext().setAttribute(ContainerBlog.class.getSimpleName(),
					containerBlog);
		}

		return containerBlog;
		
	}
	
	public RequestDispatcher getRequestDispatcher() {
		
		RequestDispatcher requestDispatcher = null;
		
		Default redirect = Default.get(this);
		
		String page = null;
		if (redirect == null && (page = getBlog().getPage(this)) != null) {
			requestDispatcher = super.getRequestDispatcher(page);
		}
		
		if (redirect != null && requestDispatcher == null) {
			requestDispatcher = super.getRequestDispatcher(redirect.getPath());
		} else if (requestDispatcher == null) {
			requestDispatcher = super.getRequestDispatcher(Default.ERROR.getPath(this));
		}
		
		return requestDispatcher;
	}

}
