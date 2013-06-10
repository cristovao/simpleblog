package br.com.simpleblog.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

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
import javax.servlet.http.HttpServletResponseWrapper;

import br.com.simpleblog.taglib.BodyTag;
import br.com.simpleblog.util.Default;

@WebFilter(urlPatterns="/web/*")
public class DecoratorFilter implements Filter {
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;

		if (Default.isValid(httpServletRequest)) {
			BufferedHttpResponseWrapper bufferedHttpResponseWrapper = new BufferedHttpResponseWrapper(httpServletResponse);
			
			chain.doFilter(request, bufferedHttpResponseWrapper);
			
			request.setAttribute(BodyTag.class.getSimpleName(), new String(bufferedHttpResponseWrapper.getOutput()));
			
			String decorator = Default.DECORATOR.getPath(httpServletRequest);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(decorator);
			
			requestDispatcher.forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
		
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

}