package com.lugq.app.network.servlet.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.lugq.app.config.AppConfig;

@WebFilter(filterName="FileFilter", urlPatterns="/file")
public class FileFilter implements Filter {

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		if (AppConfig.getInstance().getServerConfig().getFileServer().isEnable()) {
			chain.doFilter(req, resp);
		} else {
			resp.getWriter().write("bad access");
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		
	}
}
