package com.km66.framework.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 解决请求跨域问题 在Core不加注解,不启动 在使用的子项目中处理
 * 
 * @projectName: [knowledge-core]
 * @author: [Sir丶雨轩]
 * @createDate: [2019年4月28日 下午4:24:26]
 * @version: [v1.0]
 */
public class BaseCorsFilter implements Filter {
	private String allowMethods = "POST, GET, OPTIONS, DELETE ,PUT";
	private String allowHeaders = "";

	public void setAllowMethods(String allowMethods) {
		this.allowMethods = allowMethods;
	}

	public void setAllowHeaders(String allowHeaders) {
		this.allowHeaders = allowHeaders;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods", allowMethods);
		res.setHeader("Access-Control-Max-Age", "3600");
		res.setHeader("Access-Control-Allow-Headers",
				"Content-Type, x-requested-with, X-Custom-Header," + allowHeaders);
		if ("OPTIONS".equals(req.getMethod())) {
			res.setStatus(204);
			chain.doFilter(request, response);
			return;
		}
		String ContentType = request.getContentType();
		if (ContentType != null && ContentType.length() > 0) {
			if (ContentType.startsWith("application/json")) {
				ServletRequest requestWrapper = null;
				if (request instanceof HttpServletRequest) {
					requestWrapper = new RequestWrapper((HttpServletRequest) request);
				}
				if (requestWrapper == null) {
					chain.doFilter(request, response);
				} else {
					chain.doFilter(requestWrapper, response);
				}

			} else {
				chain.doFilter(request, response);
			}
		} else {
			chain.doFilter(request, response);
		}

	}

	public void destroy() {

	}
}
