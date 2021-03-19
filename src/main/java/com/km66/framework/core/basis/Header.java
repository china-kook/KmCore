package com.km66.framework.core.basis;

import com.km66.framework.core.utils.web.WebUtil;

import cn.hutool.core.convert.Convert;

/**
 * header数据读取工具
 * 
 * @projectName: [framework-core]
 * @author: [Sir丶雨轩]
 * @createDate: [2019年4月29日 下午12:27:57]
 * @version: [v1.0]
 */
public class Header {

	/**
	 * Header中获取数据
	 * 
	 * @author: [Sir丶雨轩]
	 * @createDate: [2019年4月29日 下午12:28:07]
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		return WebUtil.getHeader(key);
	}

	/**
	 * 从header中获取字符串数据,不会返回null空值返回""
	 * 
	 * @author: [Sir丶雨轩]
	 * @createDate: [2019年4月29日 下午12:29:27]
	 * @param key 键值
	 * @return 结果
	 */
	public static String getStr(String key) {
		return Convert.toStr(get(key), "");
	}

	/**
	 * 从header中获取整数数据,不会返回null空值返回0
	 * 
	 * @author: [Sir丶雨轩]
	 * @createDate: [2019年4月29日 下午12:29:57]
	 * @param key 键值
	 * @return 结果
	 */
	public static Integer getInt(String key) {
		return Convert.toInt(get(key), 0);
	}
}
