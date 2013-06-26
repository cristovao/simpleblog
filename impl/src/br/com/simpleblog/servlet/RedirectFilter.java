package br.com.simpleblog.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.simpleblog.util.Default;

@WebFilter(urlPatterns="/web/*")
public class RedirectFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		
		BlogHttpServletRequestWrapper blogHttpServletRequestWrapper = new BlogHttpServletRequestWrapper(httpServletRequest);
		
		if (blogHttpServletRequestWrapper.hasBlog()) {
			
			blogHttpServletRequestWrapper.setAttribute("blogPath", blogHttpServletRequestWrapper.getBlog().getPath());
			
			RequestDispatcher requestDispatcher = blogHttpServletRequestWrapper.getRequestDispatcher();
			
			requestDispatcher.forward(blogHttpServletRequestWrapper, response);
		} else {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(Default.ERROR.getPath(httpServletRequest));
			
			requestDispatcher.forward(blogHttpServletRequestWrapper, response);
		}
		
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

}
