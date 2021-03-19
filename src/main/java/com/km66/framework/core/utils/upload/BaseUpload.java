package com.km66.framework.core.utils.upload;

import java.io.IOException;
import java.io.InputStream;

/**
 * 上传基础类
 * 
 * @projectName: [framework-core]
 * @author: [Sir丶雨轩]
 * @createDate: [2019年4月29日 上午11:58:49]
 * @version: [v1.0]
 */
public abstract class BaseUpload {

	/**
	 * 文件上传
	 * 
	 * @author: [Sir丶雨轩]
	 * @createDate: [2019年4月29日 下午12:00:16]
	 * @param inputStream 文件流
	 * @return 上传后的文件信息
	 * @throws IOException
	 */
	public abstract FileInfo upload(InputStream inputStream) throws IOException;
}
