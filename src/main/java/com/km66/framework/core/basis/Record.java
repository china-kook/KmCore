package com.km66.framework.core.basis;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.km66.framework.core.utils.web.WebUtil;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据处理类,多参数入参必选
 *
 * @projectName: [framework-core]
 * @author: [Sir丶雨轩]
 * @createDate: [2019年4月28日 下午9:33:00]
 * @version: [v1.0]
 */
@Slf4j
public class Record extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 使用Map创建Record
     *
     * @param data map数据
     * @return 结果
     * @author: [Sir丶雨轩]
     * @createDate: [2019年4月28日 下午9:36:10]
     */
    public static Record create(Map<String, Object> data) {
        Record record = new Record();
        record.putAll(data);
        return record;
    }

    /**
     * 创建Record可自动获取Request参数
     *
     * @param isGetRequest 是否获取Request数据
     * @return 结果
     * @author: [Sir丶雨轩]
     * @createDate: [2019年4月28日 下午9:36:10]
     */
    public static Record create(boolean isGetRequest) {
        if (isGetRequest) {
            return create(WebUtil.getRequest());
        }
        return new Record();
    }

    /**
     * 创建Record自动获取Request参数
     *
     * @return 结果
     * @author: [Sir丶雨轩]
     * @createDate: [2019年4月28日 下午9:36:10]
     */
    public static Record create() {
        return create(true);
    }

    /**
     * 使用request中数据创建Record
     *
     * @param request request
     * @return 结果
     * @author: [Sir丶雨轩]
     * @createDate: [2019年4月29日 下午1:30:11]
     */
    public static Record create(HttpServletRequest request) {
        Record dict = new Record();
        Map<String, String[]> properties = request.getParameterMap();
        Iterator<?> entries = properties.entrySet().iterator();
        HashMap.Entry<?, ?> entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (HashMap.Entry<?, ?>) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            dict.put(name, value);
        }
        return dict;
    }

    /**
     * 创建Page对象,自动读取pageIndex,pageSize
     *
     * @param <T>
     * @return 结果
     * @author: [Sir丶雨轩]
     * @createDate: [2019年4月28日 下午9:42:00]
     */
    public <T> Page<T> getPage(Class<T> t) {
        return new Page<T>(this.getInt("page"), this.getInt("limit"));
    }

    /**
     * 从数据中获取String型数据,不会返回null,空数据返回""
     *
     * @param key 键值
     * @return 结果
     * @author: [Sir丶雨轩]
     * @createDate: [2019年4月28日 下午9:37:33]
     */
    public String getStr(String key) {
        return Convert.toStr(this.get(key), StrUtil.EMPTY);
    }

    /**
     * 从数据中获取Integer型数据,不会返回null,空数据返回0
     *
     * @param key
     * @return
     * @author: [Sir丶雨轩]
     * @createDate: [2019年4月28日 下午9:39:41]
     */
    public Integer getInt(String key) {
        return Convert.toInt(this.get(key), 0);
    }

    /**
     * 从数据中获取Boolean型数据,不会返回null,空数据返回false
     *
     * @param key
     * @return
     * @author: [Sir丶雨轩]
     * @createDate: [2019年4月28日 下午9:39:41]
     */
    public Boolean getBool(String key) {
        return Convert.toBool(this.get(key), false);
    }

    /**
     * 获取时间返回
     */
    public Timestamp getTimestamp(String key) {
        String val = this.getStr(key);
        if (StrUtil.isBlank(val)) {
            return null;
        }
        try {
            return new Timestamp(DateUtil.parse(val).getTime());
        } catch (Exception e) {
            log.warn("时间转换失败:key{}->value{}", key, val);
        }
        return null;
    }

    /**
     * 把自身转换成实体类返回
     *
     * @param <T>
     * @param clazz
     * @return
     * @author: [Sir丶雨轩]
     * @createDate: [2019年6月25日 上午10:45:53]
     */
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<? extends BaseEntity> clazz) {
        Map<String, Object> map = null;
        try {
            map = clazz.newInstance();
            map.putAll(this);
        } catch (InstantiationException | IllegalAccessException e) {
            log.warn("GetBean Error 实例化失败");
        }
        return (T) map;
    }

    /**
     * 从请求中获取整体的headler数据
     *
     * @return
     * @author: [Sir丶雨轩]
     * @createDate: [2019年6月25日 下午12:07:22]
     */
    public Headler getHeadler() {
        return WebUtil.getHeadler();
    }

    public QueryWrapper getQueryWrapper(String... fields) {
        if (fields.length == 0)
            return null;

        QueryWrapper queryWrapper = new QueryWrapper();
        for (String field : fields) {
            queryWrapper.eq(field, this.getStr(field));
        }
        return queryWrapper;
    }

    public String[] getArr(String key, String... split) {
        String split_ = split.length == 0 ? "," : split[0];
        return this.getStr(key).split(split_);
    }

    /**
     * 从Request中获取Excel数据
     *
     * @param fileName
     * @return
     * @throws IOException
     * @author: [Sir丶雨轩]
     * @createDate: [2019年5月13日 下午7:51:42]
     */
    @SuppressWarnings("unchecked")
    public List<Record> getExcelList(String fileName, HttpServletRequest request, int startLine) {
        List<Record> result = new ArrayList<Record>();
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile(fileName);
            if (!file.isEmpty()) {
                try {
                    List<Object> data = EasyExcelFactory.read(file.getInputStream(), new Sheet(1));
                    List<String> title = new ArrayList<String>();
                    for (int i = 0; i < data.size(); i++) {
                        if (i < startLine - 1) {
                            continue;
                        }
                        List<Object> jsonArray = (List<Object>) data.get(i);
                        if (title.isEmpty()) {
                            for (Object key : jsonArray) {
                                title.add(Convert.toStr(key, ""));
                            }
                            continue;
                        }
                        Record record = Record.create(false);
                        for (int j = 0; j < jsonArray.size(); j++) {
                            record.put(title.get(j), jsonArray.get(j) == null ? "" : jsonArray.get(j));
                        }
                        result.add(record);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
