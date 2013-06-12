package br.com.simpleblog.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;

import br.com.simpleblog.taglib.DateTag;
import br.com.simpleblog.taglib.InputBodyTag;
import br.com.simpleblog.util.Blog.PostRequest;
import br.com.simpleblog.util.Default;

@WebServlet(urlPatterns = "/post")
public class PostServlet extends HttpServlet {

	@Override
	protected void doGet(BlogHttpServletRequestWrapper req,
			HttpServletResponse resp) throws ServletException, IOException {

		String post = Default.POST.getPath(req);

		try {
			if (req.getBlog().existsPost(req)) {
				
				BufferedHttpResponseWrapper bufferedHttpResponseWrapper = new BufferedHttpResponseWrapper(
						resp);
				
				PostRequest postRequest = req.getBlog().getPost(req);
				
				RequestDispatcher includerequestDispatcher = req
						.getRequestDispatcher(postRequest.getPathJSP());
				
				req.setAttribute("postPath", postRequest.getPath());
				
				req.setAttribute(DateTag.class.getSimpleName(), postRequest.getPostDate());
				
				includerequestDispatcher.include(req, bufferedHttpResponseWrapper);
				
				req.setAttribute(InputBodyTag.class.getSimpleName(), new String(
						bufferedHttpResponseWrapper.getOutput()).trim());
				
			} else {
				post = Default.ERROR.getPath(req);
			}
		} catch (Exception e) {
			e.printStackTrace();
			post = Default.ERROR.getPath(req);
		}

		RequestDispatcher requestDispatcher = req.getRequestDispatcher(post);

		requestDispatcher.include(req, resp);
	}
}
