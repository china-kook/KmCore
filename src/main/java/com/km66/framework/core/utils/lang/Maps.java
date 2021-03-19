package com.km66.framework.core.utils.lang;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.StrUtil;

import java.util.HashMap;
import java.util.Map;

public class Maps {

    public Maps(Map<?, ?> map) {
        this.innerMap = map;
    }

    private Map<?, ?> innerMap;
    private static volatile Maps maps;

   /* public static Maps getInstance(Map<?, ?> map) {

        if (map == null) {
            throw new NullPointerException("parameter canNotBeEmpty");
        }

        if (maps == null) {
            synchronized (Maps.class) {
                if (maps == null) {
                    maps = new Maps(map);
                }
            }
        }
        return maps;
    }*/

    public int getInt(String key, int defaultVal) {
        if (innerMap.containsKey(key)) {
            return Convert.toInt(innerMap.get(key), defaultVal);
        }
        return defaultVal;
    }

    public int getInt(String key) {
        return this.getInt(key, 0);
    }

    public String getStr(String key, String defaultVal) {
        if (innerMap.containsKey(key)) {
            return Convert.toStr(innerMap.get(key), defaultVal);
        }
        return defaultVal;
    }

    public String getStr(String key) {
        return this.getStr(key, StrUtil.EMPTY);
    }




}
