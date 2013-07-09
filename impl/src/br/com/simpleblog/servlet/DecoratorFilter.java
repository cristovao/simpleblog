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

import br.com.simpleblog.taglib.BodyTag;
import br.com.simpleblog.util.Default;

@WebFilter(urlPatterns="/*")
public class DecoratorFilter implements Filter {
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;

		if (httpServletRequest.getRequestURI().matches("/.*/[A-Za-z_0-9]*\\.[A-Za-z_0-9]*")) {
			chain.doFilter(request, response);
		} 
		
		BlogHttpServletRequestWrapper blogHttpServletRequestWrapper = new BlogHttpServletRequestWrapper(httpServletRequest);
		
		if (blogHttpServletRequestWrapper.hasBlog()) {
			BufferedHttpResponseWrapper bufferedHttpResponseWrapper = new BufferedHttpResponseWrapper(httpServletResponse);
			
			chain.doFilter(request, bufferedHttpResponseWrapper);
			
			request.setAttribute(BodyTag.class.getSimpleName(), new String(bufferedHttpResponseWrapper.getOutput()));
			
			String decorator = Default.DECORATOR.getPath(httpServletRequest);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(decorator);
			
			requestDispatcher.forward(request, response);
		}
		
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

}
