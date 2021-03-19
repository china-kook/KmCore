package com.km66.framework.core.basis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 基础Controller所有Controller必须继承
 * @projectName:  [framework-core]    
 * @author:       [Sir丶雨轩]   
 * @createDate:   [2019年4月28日 下午4:22:45]   
 * @version:      [v1.0]
 */
public class BaseController {

	@Autowired
	protected HttpServletResponse response;
	
	@Autowired
	protected HttpServletRequest request;
}
