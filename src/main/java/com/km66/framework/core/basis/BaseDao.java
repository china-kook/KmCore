package com.km66.framework.core.basis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 基础Dao所有Mapper必须继承
 * 
 * @projectName: [framework-core]
 * @author: [Sir丶雨轩]
 * @createDate: [2019年4月28日 下午4:16:28]
 * @version: [v1.0]
 */
public interface BaseDao<T extends BaseEntity> extends BaseMapper<T> {

}
