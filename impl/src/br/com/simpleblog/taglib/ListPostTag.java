package br.com.simpleblog.taglib;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import br.com.simpleblog.servlet.BufferedHttpResponseWrapper;
import br.com.simpleblog.util.Blog;
import br.com.simpleblog.util.Blog.PostRequest;

public class ListPostTag extends BodyTagSupport {

	private Queue<Post> postQueue = new LinkedList<ListPostTag.Post>();

	public static class Post {
		private String body;
		private String link;
		private String title;
		private Date date;

		public Post(String body, String link, String title, Date date) {
			this.body = body;
			this.link = link;
			this.date = date;
			if (title != null) {
				this.title = title;
			} else {
				this.title = "";
			}
		}

		public String getBody() {
			return body;
		}

		public String getLink() {
			return link;
		}

		public String getTitle() {
			return title;
		}
		
		public Date getDate() {
			return date;
		}
	}

	private String var;
	private Date date;

	@Override
	public int doStartTag() throws JspException {
		Blog blog = (Blog) pageContext.getRequest().getAttribute(
				Blog.class.getSimpleName());

		if (blog != null) {
			Set<PostRequest> postRequestSet = blog.getPathPostList(
					pageContext.getServletContext(), getDate());

			for (PostRequest postRequest : postRequestSet) {
				try {
					Post post = getPost(postRequest);
					if (post != null)
						postQueue.add(post);
				} catch (Exception e) {
					throw new JspException(e);
				}
			}
		}

		if (!postQueue.isEmpty()) {
			Post poll = postQueue.poll();
			pageContext.setAttribute(DateTag.class.getSimpleName(), poll.getDate());
			pageContext.setAttribute(getVar(), poll);

			return EVAL_BODY_INCLUDE;
		}

		return SKIP_BODY;
	}

	@Override
	public int doAfterBody() throws JspException {

		if (!postQueue.isEmpty()) {
			Post poll = postQueue.poll();
			pageContext.setAttribute(DateTag.class.getSimpleName(), poll.getDate());
			pageContext.setAttribute(getVar(), poll);

			return EVAL_BODY_AGAIN;
		}

		return super.doAfterBody();
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	private Post getPost(PostRequest postRequest) throws ServletException,
			IOException {

		Post post = null;

		if (pageContext.getResponse() instanceof HttpServletResponse
				&& pageContext.getRequest() instanceof HttpServletRequest) {
			HttpServletRequest request = (HttpServletRequest) pageContext
					.getRequest();

			BufferedHttpResponseWrapper wrapper = new BufferedHttpResponseWrapper(
					(HttpServletResponse) pageContext.getResponse());

			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher(postRequest.getPathJSP());

			requestDispatcher.include(request, wrapper);

			String title = (String) pageContext.getRequest().getAttribute(
					InputTitleTag.class.getSimpleName());

			String body = (String) pageContext.getRequest().getAttribute(
					PreviewTag.class.getSimpleName());
			
			pageContext.getRequest().removeAttribute(PreviewTag.class.getSimpleName());
			
			if (body == null || body.isEmpty()) {
				body = wrapper.getOutput().trim();
			}
			
			post = new Post(body, postRequest.getWebPathPost(), title, postRequest.getPostDate());
		}

		return post;
	}
}
