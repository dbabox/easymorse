package com.easymorse;

import javax.servlet.http.HttpServlet;

import org.apache.log4j.PropertyConfigurator;

/**
 * Servlet implementation class Log4jInitServlet
 */
public class Log4jInitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Log4jInitServlet() {
		super();
		PropertyConfigurator.configure(Thread.currentThread()
				.getContextClassLoader().getResource("log4j.properties"));
	}

}
