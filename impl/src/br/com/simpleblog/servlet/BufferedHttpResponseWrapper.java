package br.com.simpleblog.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class BufferedHttpResponseWrapper extends HttpServletResponseWrapper {

	private PrintWriter writer = null;
	private ByteArrayOutputStream baos = null;

	public BufferedHttpResponseWrapper(HttpServletResponse response) {
		super(response);
		baos = new ByteArrayOutputStream();
		writer = new PrintWriter(baos);
	}

	public PrintWriter getWriter() throws IOException {
		return writer;
	}

	public String getOutput() {
		writer.flush();
		writer.close();
		return baos.toString();
	}
}
