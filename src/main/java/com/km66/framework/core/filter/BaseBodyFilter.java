package com.km66.framework.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 处理流的问题 解决传递JSON时只能获取一次的问题 在Core不加注解,不启动 在使用的子项目中处理
 * 
 * @projectName: [framework-core]
 * @author: [Sir丶雨轩]
 * @createDate: [2019年4月28日 下午4:27:17]
 * @version: [v1.0]
 */
public class BaseBodyFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (request.getContentType() != null && request.getContentType().contains("json")) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			RequestWrapper requestWrapper = new RequestWrapper(httpServletRequest);
			chain.doFilter(requestWrapper, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {

	}

}
