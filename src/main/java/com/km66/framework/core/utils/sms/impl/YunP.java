package com.km66.framework.core.utils.sms.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.km66.framework.core.basis.RespEntity;

import java.util.HashMap;
import java.util.Map;

public class YunP {

    public static String send(String phone, String code) {
        Map<String, Object> params = new HashMap<>();
        params.put("apikey", "7bcbc5d87e5909d4e2659d67720a8593");
        params.put("text", "【66智慧门店】您的验证码是" + code + ",3分钟有效。如非本人操作，请忽略本短信。");
        params.put("mobile", phone);
        String result = HttpUtil.post("https://sms.yunpian.com/v2/sms/single_send.json", params);
        return result;
    }
}
