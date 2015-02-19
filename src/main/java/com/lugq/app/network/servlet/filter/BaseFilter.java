package com.lugq.app.network.servlet.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(filterName="BaseFilter",urlPatterns="/*")
public class BaseFilter implements Filter {

	// 限制访问频率 1秒最多MAX_REQ_PER_SECOND次
	private Map<String, Long[]> reqFrequencyMap;
	private final int MAX_REQ_PER_SECOND = 5;
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		reqFrequencyMap = new HashMap<String, Long[]>();
	}
	
	@Override
	public void destroy() {
		reqFrequencyMap = null;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		long timestamp = System.currentTimeMillis();
		String ip = req.getRemoteAddr();
		Long[] frequency = reqFrequencyMap.get(ip); 
		if (frequency != null) {
			long count = frequency[0];  
			long latestReqTime = frequency[1];
			if (timestamp - latestReqTime < 1000) {
				if (count++ == MAX_REQ_PER_SECOND) {
					return;
				} else {
					frequency[0] = count;
				}
			} else {
				frequency = new Long[]{1L, timestamp};
			}
		} else {
			frequency = new Long[]{1L, timestamp};
		}
		reqFrequencyMap.put(ip, frequency);
		chain.doFilter(req, resp);
	}
}
