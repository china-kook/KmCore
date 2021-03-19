package com.km66.framework.core.utils.configkit;

import cn.hutool.core.convert.Convert;
import cn.hutool.setting.Setting;

/**
 * app.setting 读取工具类
 * 
 * @projectName: [framework-core]
 * @author: [Sir丶雨轩]
 * @createDate: [2019年4月28日 下午4:29:17]
 * @version: [v1.0]
 */
public final class CoreKit {

	protected static Setting setting;
	static {
		setting = new Setting("config/core.setting");
	}

	public static String getStr(String key) {
		return Convert.toStr(setting.get(key), "");
	}

	public static String getStr(String key, String gp) {
		return setting.getStr(key, gp, "");
	}

	public static Integer getInt(String key) {
		return Convert.toInt(getStr(key), 0);
	}

	public static Integer getInt(String key, String gp) {
		return Convert.toInt(getStr(key, gp), 0);
	}

	public static Long getLong(String key, String gp) {
		return Convert.toLong(getStr(key, gp), 0l);
	}

	public static Long getLong(String key) {
		return Convert.toLong(getStr(key), 0l);
	}

}
