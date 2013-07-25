package br.com.simpleblog.util;

import java.io.File;
import java.io.FileFilter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class Blog {
	private static final String PATH_POST = "post";

	public class PostRequest implements Comparable<PostRequest> {
		private static final String POST_FILE = "post.jsp";

		private Date postDate;
		private String path;

		public PostRequest(String path) throws ParseException {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd/kkmmss");

			this.path = path;

			this.postDate = dateFormat.parse(path);
		}

		public Date getPostDate() {
			return postDate;
		}

		public String getPath() {
			return "/" + getName() + "/" + PATH_POST + "/" + path;
		}

		public String getPathJSP() {
			return getPath() + "/" + POST_FILE;
		}

		public String getWebPathPost() {
			return getPath();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((path == null) ? 0 : path.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PostRequest other = (PostRequest) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (path == null) {
				if (other.path != null)
					return false;
			} else if (!path.equals(other.path))
				return false;
			return true;
		}

		@Override
		public int compareTo(PostRequest o) {
			return this.path.compareTo(o.path);
		}

		private Blog getOuterType() {
			return Blog.this;
		}
	}

	public static class DateFileFilter implements FileFilter {

		private String name;
		private String minus;

		public DateFileFilter(String name, String minus) {
			this.name = name;
			this.minus = minus;
		}

		@Override
		public boolean accept(File file) {
			return file.isDirectory()
					&& (name + file.getName()).compareTo(minus) <= 0;
		}

	}

	private String name;

	public Blog(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return "/" + getName();
	}

	public PostRequest getPost(HttpServletRequest request)
			throws ParseException {

		String requestURI = (String) request.getRequestURI();

		if (!requestURI.startsWith("/")) {
			requestURI = "/" + requestURI;
		}

		requestURI = requestURI.replaceFirst("/" + getName() + "/" + PATH_POST
				+ "/", "");

		if (requestURI.endsWith("/")) {
			requestURI = requestURI.substring(0, requestURI.length() - 1);
		}

		return new PostRequest(requestURI);
	}

	public boolean existsPost(HttpServletRequest request) {

		try {
			String post = getPost(request).getPathJSP();

			return request.getServletContext().getResourceAsStream(post) != null;
		} catch (ParseException e) {
			return false;
		}
	}

	public Set<PostRequest> getPathPostList(ServletContext context, Date date) {
		Set<PostRequest> postRequestSet = new HashSet<Blog.PostRequest>();

		String realPath = context
				.getRealPath("/" + getName() + "/" + PATH_POST);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		File file = new File(realPath);

		if (file.exists()) {
			String yearMinus = convert(calendar.get(Calendar.YEAR));
			String monthMinus = yearMinus + ""
					+ convert(calendar.get(Calendar.MONTH) + 1);
			String dayMinus = monthMinus + ""
					+ convert(calendar.get(Calendar.DAY_OF_MONTH));
			String hourMinus = dayMinus + ""
					+ convert(calendar.get(Calendar.HOUR_OF_DAY)) + ""
					+ convert(calendar.get(Calendar.MINUTE)) + ""
					+ convert(calendar.get(Calendar.SECOND));

			File[] yearArray = listFiles("", file, yearMinus);

			for (File year : yearArray) {
				File[] monthArray = listFiles(year.getName(), year, monthMinus);

				for (File month : monthArray) {
					File[] dayArray = listFiles(
							year.getName() + month.getName(), month, dayMinus);

					for (File day : dayArray) {

						File[] hourArray = listFiles(
								year.getName() + month.getName()
										+ day.getName(), day, hourMinus);

						for (File hour : hourArray) {
							StringBuilder pathBuilder = new StringBuilder();
							pathBuilder.append(year.getName());
							pathBuilder.append("/");
							pathBuilder.append(month.getName());
							pathBuilder.append("/");
							pathBuilder.append(day.getName());
							pathBuilder.append("/");
							pathBuilder.append(hour.getName());

							String path = pathBuilder.toString();

							try {
								postRequestSet.add(new PostRequest(path));
							} catch (ParseException e) {
								System.out.println(e.getMessage());
							}
						}
					}
				}
			}
		}

		TreeSet<PostRequest> treeSet = new TreeSet<Blog.PostRequest>(
				Collections.reverseOrder(new Comparator<PostRequest>() {

					@Override
					public int compare(PostRequest o1, PostRequest o2) {
						return o1.compareTo(o2);
					}

				}));

		treeSet.addAll(postRequestSet);

		return treeSet;
	}

	private File[] listFiles(String prefix, File file, String minusText) {

		return file.listFiles(new DateFileFilter(prefix, minusText));
	}

	private String convert(int value) {
		if (value < 10) {
			return "0" + value;
		}

		return "" + value;
	}

	public String getPage(HttpServletRequest request) {
		String requestURI = (String) request.getRequestURI();

		String[] uriArray = requestURI.split("/");

		if (uriArray.length == 2) {
			String path = "/" + uriArray[0] + "/" + uriArray[1]+ ".jsp";

			if (request.getServletContext().getResourceAsStream(path) != null) {
				return path;
			}
		}
		return null;
	}

}
