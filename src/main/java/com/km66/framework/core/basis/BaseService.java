package com.km66.framework.core.basis;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;

/**
 * 基础Service所有Service必须继承
 * 
 * @projectName: [framework-core]
 * @author: [Sir丶雨轩]
 * @createDate: [2019年4月28日 下午4:19:30]
 * @version: [v1.0]
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BaseService {

	
	public List<?> toTimeStr(List<? extends Map> list, String datePattern) {
		for (Map map : list) {
			map.forEach((k, v) -> {
				if (v instanceof Timestamp) {
					map.put(k, DateUtil.format(new Date(((Timestamp) v).getTime()), datePattern));
				}
			});
		}
		return list;
	}

	public Map<String, Object> toTimeStr(Map param, String datePattern) {
		param.forEach((k, v) -> {
			if (v instanceof Timestamp) {
				param.put(k, DateUtil.format(new Date(((Timestamp) v).getTime()), datePattern));
			}
		});
		return param;
	}

	public Map<String, Object> toTimeStr(Map param) {
		return toTimeStr(param, DatePattern.NORM_DATE_PATTERN);
	}

	public List<?> toTimeStr(List<? extends Map> list) {
		return toTimeStr(list, DatePattern.NORM_DATE_PATTERN);
	}

	public IPage toTimeStr(IPage iPage, String datePattern) {
		List list = iPage.getRecords();
		iPage.setRecords(toTimeStr(list, datePattern));
		return iPage;
	}

	public IPage toTimeStr(IPage iPage) {
		return toTimeStr(iPage, DatePattern.NORM_DATE_PATTERN);
	}

}
