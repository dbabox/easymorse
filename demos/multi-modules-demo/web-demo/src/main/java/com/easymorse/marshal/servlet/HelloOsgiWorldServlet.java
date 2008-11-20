package com.easymorse.marshal.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.derby.jdbc.EmbeddedDriver;

import com.easymorse.marshal.App;

public class HelloOsgiWorldServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");

		ServletOutputStream out = resp.getOutputStream();
		out.println("<html>");
		out.println("<head><title>Hello Osgi World</title></head>");
		out.println("<body>");
		out.println("<h1>Hello OSGi World</h1>");
//		out.println("<h1>Connection test:" + App.testConnection() + "</h1>");
		out.println("<h1>Connection test:" + App.testHibernate() + "</h1>");
		out.println("</body></html>");
	}
}
