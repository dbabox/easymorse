package com.easymorse;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class DownloadResourceFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		response.setContentType("application/x-download");
		((HttpServletResponse)response).setHeader("Content-Disposition", "attachment;filename=a.gif");
		chain.doFilter(request,response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

}
