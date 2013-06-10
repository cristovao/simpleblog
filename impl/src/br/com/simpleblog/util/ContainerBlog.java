package br.com.simpleblog.util;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

public class ContainerBlog {

	private Map<String, Blog> blogMap;

	public ContainerBlog(ServletContext context) {
		this.blogMap = new HashMap<String, Blog>();

		String realPath = context.getRealPath("/");

		File file = new File(realPath);

		File[] blogArray = file.listFiles(new FileFilter() {

			@Override
			public boolean accept(File file) {
				return file.isDirectory() && !file.getName().equals("META-INF")
						&& !file.getName().equals("WEB-INF")
						&& !file.getName().equals("default");
			}
		});

		for (File blog : blogArray) {
			this.blogMap.put(blog.getName(), new Blog(blog.getName()));
		}
	}
	
	public Blog get(String name) {
		return this.blogMap.get(name);
	}
}
