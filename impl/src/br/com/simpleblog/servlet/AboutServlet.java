package br.com.simpleblog.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.simpleblog.util.Default;

@WebServlet(urlPatterns = "/about")
public class AboutServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String main = Default.ABOUT.getPath(req);
		
		RequestDispatcher requestDispatcher = req
				.getRequestDispatcher(main);

		requestDispatcher.include(req, resp);

	}

}
