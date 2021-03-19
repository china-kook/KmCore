package com.km66.framework.core.basis;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;

import cn.hutool.core.lang.Dict;

public class PageEntity extends JSONObject {

	private static final long serialVersionUID = 1L;
	private static PageEntity pageEntity = new PageEntity();

    public static PageEntity create(IPage<?> page) {
        pageEntity = new PageEntity();
        pageEntity.put("code", 0);
        pageEntity.put("msg", "");
        Record data = Record.create(false);
        data.put("TotalSize", page.getTotal());
        data.put("pageSize", page.getSize());
        data.put("Items", page.getRecords());
        pageEntity.put("data", data);
        return pageEntity;
    }

    public static PageEntity create(List<?> data) {
        pageEntity.put("code", 0);
        pageEntity.put("msg", "");
        Dict dict = Dict.create();
        dict.put("TotalSize", data.size());
        dict.put("pageSize", data.size());
        dict.put("Items", data);
        pageEntity.put("data", dict);
        return pageEntity;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}