package br.com.simpleblog.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.simpleblog.util.Default;

@WebServlet(urlPatterns = "/main")
public class MainServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Date date = new Date();

		String main = Default.MAIN.getPath(req);
		
		req.setAttribute("date", date);
		
		RequestDispatcher requestDispatcher = req
				.getRequestDispatcher(main);

		requestDispatcher.include(req, resp);

	}

}
