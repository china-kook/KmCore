package com.km66.framework.core.utils.program;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;

import cn.hutool.core.lang.caller.CallerUtil;

/**
 * SpringBoot程序启动工具
 * 
 * @projectName: [framework-core]
 * @author: [Sir丶雨轩]
 * @createDate: [2019年4月28日 下午10:41:20]
 * @version: [v1.0]
 */
@Deprecated
public class ProgramUtil {

	@Value("${server.port}")
	private int port;

	/**
	 * 启动程序
	 * 
	 * @author: [Sir丶雨轩]
	 * @createDate: [2019年4月28日 下午10:41:29]
	 * @param args 入参
	 */
	public static void run(String... args) {
		SpringApplication application = new SpringApplication(CallerUtil.getCaller(3));
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
	}

	/**
	 * 重启程序,先杀死进程,在启动
	 * 
	 * @author: [Sir丶雨轩]
	 * @createDate: [2019年4月28日 下午10:55:02]
	 * @param args
	 */
	public static void restart(String... args) {
		/*
		 * String os = System.getProperty("os.name"); if
		 * (os.toLowerCase().startsWith("win")) { KillUtil.start(20003); }
		 */
		run(args);
	}
}
