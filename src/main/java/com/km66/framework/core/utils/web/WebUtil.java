package com.km66.framework.core.utils.web;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.km66.framework.core.basis.Headler;

/**
 * WebUtil 处理Request及Response
 *
 * @projectName: [framework-core]
 * @author: [Sir丶雨轩]
 * @createDate: [2019年4月28日 下午4:34:59]
 * @version: [v1.0]
 */
public final class WebUtil {

    /**
     * 返回HttpServletRequest操作对象
     *
     * @return HttpServletRequest
     * @author: [Sir丶雨轩]
     * @createDate: [2019年4月28日 下午4:35:46]
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 返回HttpServletResponse操作对象
     *
     * @return HttpServletResponse
     * @author: [Sir丶雨轩]
     * @createDate: [2019年4月28日 下午4:36:02]
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 判断本次请求是否为Ajax
     *
     * @param req HttpServletRequest
     * @return 是=true,不是=false
     * @author: [Sir丶雨轩]
     * @createDate: [2019年5月13日 下午12:49:25]
     */
    public static boolean isAjax(HttpServletRequest req) {
        return req.getHeader("x-requested-with") != null
                && req.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest");
    }

    /**
     * 判断本次请求是否为Ajax,获取当前线程的HttpServletRequest <br>
     * {@link com.km66.framework.core.utils.WebUtil.getRequest()}
     *
     * @return 是=true,不是=false
     * @author: [Sir丶雨轩]
     * @createDate: [2019年5月13日 下午12:49:25]
     */
    public static boolean isAjax() {
        return isAjax(getRequest());
    }

    /**
     * 获取Header数据
     *
     * @param key header key
     * @return 结果数据
     * @author: [Sir丶雨轩]
     * @createDate: [2019年4月28日 下午4:36:18]
     */
    public static String getHeader(String key) {
        return getRequest().getHeader(key);
    }

    /**
     * 从请求中获取整体的Headler数据
     *
     * @return
     * @author: [Sir丶雨轩]
     * @createDate: [2019年6月25日 下午12:08:31]
     */
    public static Headler getHeadler() {
        Headler headler = new Headler();
        Enumeration<?> enumer = getRequest().getHeaderNames();
        while (enumer.hasMoreElements()) {
            String key = (String) enumer.nextElement();
            String value = WebUtil.getHeader(key);
            headler.put(key, value);
        }
        return headler;
    }

    /**
     * 设置Session
     *
     * @param key   键
     * @param value 值
     * @author: [Sir丶雨轩]
     * @createDate: [2019年5月13日 下午1:03:45]
     */
    public static void setSession(String key, Object value) {
        getRequest().getSession().setAttribute(key, value);
    }


	/**
	 * 获取客户端真实IP
	 * @return IP
	 */
	public static String getIP() {
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;

    }
}
