package com.easymorse.videos.server;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class FormAuthenticationFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse httpServletResponse = new AuthenticationResponseWrapper(
				(HttpServletResponse) response);
		chain.doFilter(request, httpServletResponse);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

}

class AuthenticationResponseWrapper extends HttpServletResponseWrapper {

	public AuthenticationResponseWrapper(HttpServletResponse response) {
		super(response);
	}

	@Override
	public void sendRedirect(String location) throws IOException {
		if (location.contains("spring_security_login")) {
			this.sendError(401, "need login");
		} else {
			if (!location.endsWith("json")) {
				super.sendRedirect(location);
			}
		}
	}

}
