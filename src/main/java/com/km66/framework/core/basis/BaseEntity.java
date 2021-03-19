package com.km66.framework.core.basis;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
/**
 * 基础实体类,所有实体类必须继承,如子项目有特殊需求可继承此类来进行操作
 * @projectName:  [framework-core]    
 * @author:       [Sir丶雨轩]   
 * @createDate:   [2019年4月28日 下午8:41:05]   
 * @version:      [v1.0]
 */
public abstract class BaseEntity extends JSONObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public BaseEntity() {
	}
	public BaseEntity(Record record) {
		this.putAll(record);
	}
	
	/**
	 * 由于MyBatisPlus CRUD会自动把私有字段当作列进行查询<br>
	 * 手动过滤父类JSONObject中的map禁止查询<br>
	 * map 在此项目中不能被当成字段名
	 */
	@TableField(exist = false)
	private Object map;

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
