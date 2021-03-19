package com.km66.framework.core.utils.program;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

/**
 * 杀死进程工具类,仅在开发阶段使用,用于在Eclipse下快捷重启项目
 * 
 * @projectName: [framework-core]
 * @author: [Sir丶雨轩]
 * @createDate: [2019年4月28日 下午10:51:22]
 * @version: [v1.0]
 */
@Slf4j
@Deprecated
public class KillUtil {
	private static Set<Integer> ports;

	/**
	 * 杀死指定端口号进程
	 * 
	 * @author: [Sir丶雨轩]
	 * @createDate: [2019年4月28日 下午10:52:34]
	 * @param port 端口号
	 */
	public static void start(int port) {
		ports = new HashSet<Integer>();
		ports.add(port);
		Runtime runtime = Runtime.getRuntime();
		try {
			// 查找进程号
			Process p = runtime.exec("cmd /c netstat -ano | findstr \"" + port + "\"");
			InputStream inputStream = p.getInputStream();
			List<String> read = read(inputStream, "UTF-8");
			if (read.size() == 0) {
				log.warn("找不到该端口的进程");
			} else {
				log.info("找到{}个进程，正在准备清理", read.size());
				kill(read);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 验证此行是否为指定的端口，因为 findstr命令会是把包含的找出来，例如查找80端口，但是会把8099查找出来
	 * 
	 * @author: [Sir丶雨轩]
	 * @createDate: [2019年4月28日 下午10:52:51]
	 * @param str
	 * @return
	 */
	private static boolean validPort(String str) {
		Pattern pattern = Pattern.compile("^ *[a-zA-Z]+ +\\S+");
		Matcher matcher = pattern.matcher(str);

		matcher.find();
		String find = matcher.group();
		int spstart = find.lastIndexOf(":");
		find = find.substring(spstart + 1);

		int port = 0;
		try {
			port = Integer.parseInt(find);
		} catch (NumberFormatException e) {
			log.error("查找到错误的端口:{}", find);
			return false;
		}
		if (ports.contains(port)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 更换为一个Set，去掉重复的pid值
	 * @author:       [Sir丶雨轩]   
	 * @createDate:   [2019年4月28日 下午10:53:09]   
	 * @param data
	 */
	public static void kill(List<String> data) {
		Set<Integer> pids = new HashSet<>();
		for (String line : data) {
			int offset = line.lastIndexOf(" ");
			String spid = line.substring(offset);
			spid = spid.replaceAll(" ", "");
			int pid = 0;
			try {
				pid = Integer.parseInt(spid);
			} catch (NumberFormatException e) {
				log.error("获取的进程号错误:{}" ,spid);
			}
			pids.add(pid);
		}
		killWithPid(pids);
	}

	/**
	 * 一次性杀除所有的端口
	 * @author:       [Sir丶雨轩]   
	 * @createDate:   [2019年4月28日 下午10:53:29]   
	 * @param pids
	 */
	public static void killWithPid(Set<Integer> pids) {
		for (Integer pid : pids) {
			try {
				Runtime.getRuntime().exec("taskkill /F /pid " + pid + "");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static List<String> read(InputStream in, String charset) throws IOException {
		List<String> data = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
		String line;
		while ((line = reader.readLine()) != null) {
			boolean validPort = validPort(line);
			if (validPort) {
				data.add(line);
			}
		}
		reader.close();
		return data;
	}

	public static String readTxt(InputStream in, String charset) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
		StringBuffer sb = new StringBuffer();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		reader.close();
		return sb.toString();
	}
}