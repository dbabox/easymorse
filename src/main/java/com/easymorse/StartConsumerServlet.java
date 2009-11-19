package com.easymorse;

import org.apache.log4j.Logger;

import java.util.Enumeration;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class StartConsumerServlet extends HttpServlet implements
		ServletContextAttributeListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(StartConsumerServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String APPLICATON_ATTRIBUTE_NAME = "groovy.proto.start.consumer";

	@Override
	public void init() throws ServletException {
		super.init();
		this.getServletContext().setAttribute(APPLICATON_ATTRIBUTE_NAME, true);
	}

	@Override
	public void attributeAdded(ServletContextAttributeEvent event) {
		if (event.getName().equals(APPLICATON_ATTRIBUTE_NAME)) {
			if ((Boolean) event.getValue()) {
				logger.error(">>>>start consumer.");
			}
		}
	}

	@Override
	public void attributeRemoved(ServletContextAttributeEvent event) {
		if (event.getName().equals(APPLICATON_ATTRIBUTE_NAME)) {
			logger.error(">>>stop consumer.");
		}
	}

	@Override
	public void attributeReplaced(ServletContextAttributeEvent event) {
		if (event.getName().equals(APPLICATON_ATTRIBUTE_NAME)) {
			if (event.getServletContext().getAttribute(
					APPLICATON_ATTRIBUTE_NAME).equals(true)) {
				logger.error(">>>start consumer.");
			}else{
				logger.error(">>>stop consumer.");
			}
		}
	}
}
