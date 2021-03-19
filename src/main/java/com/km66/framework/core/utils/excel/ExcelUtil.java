package com.km66.framework.core.utils.excel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.km66.framework.core.utils.web.WebUtil;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelWriter;

/**
 * Excel工具，处理导入导出等功能
 * 
 * @projectName: [framework-core]
 * @author: [Sir丶雨轩]
 * @createDate: [2019年4月28日 下午4:30:21]
 * @version: [v1.0]
 */
public class ExcelUtil {

	/**
	 * 导出Excel
	 * 
	 * @author: [Sir丶雨轩]
	 * @createDate: [2019年4月28日 下午4:38:02]
	 * @param data        要导出的数据
	 * @param nameMapping 表头与数据库字段对应关系
	 * @param fileName    要下载的文件名
	 * @throws IOException
	 */
	public void export(List<Object> data, Map<String, String> nameMapping, String fileName) throws IOException {
		ExcelWriter writer = cn.hutool.poi.excel.ExcelUtil.getWriter();
		// 设置表头对应关系
		if (nameMapping != null)
			writer.setHeaderAlias(nameMapping);
		HttpServletResponse response = WebUtil.getResponse();
		response.setContentType("application/msexcel;charset=UTF-8");
		response.setHeader("Content-disposition",
				"attachment; filename=" + new String(fileName.getBytes("UTF-8"), "ISO_8859_1") + ".xls");
		writer.write(data);
		OutputStream out = response.getOutputStream();
		writer.flush(out);
		// 关闭writer，释放内存
		writer.close();
		out.close();

	}

	/**
	 * 从request导入excel数据
	 * 
	 * @author: [Sir丶雨轩]
	 * @createDate: [2019年4月28日 下午6:13:53]
	 * @param file      前台文件域名称
	 * @param titleLine 标题行
	 * @param startLine 开始行
	 * @return 导入的数据
	 * @throws IOException
	 */
	public List<?> importExcel(String file, Integer titleLine, Integer startLine) throws IOException {
		HttpServletRequest request = WebUtil.getRequest();
		if (!(request instanceof MultipartHttpServletRequest))
			return null;

		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		InputStream is = multipartHttpServletRequest.getFile(file).getInputStream();
		ExcelReader reader = cn.hutool.poi.excel.ExcelUtil.getReader(is);
		List<?> list = reader.read(titleLine == null ? 0 : titleLine, startLine == null ? 1 : startLine,
				Integer.MAX_VALUE, Map.class);
		return list;

	}
}
