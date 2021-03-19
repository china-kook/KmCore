package com.km66.framework.core.basis;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;


/**
 * Ajax请求返回实体,如子项目有特殊需求可继承此类来进行操作
 *
 * @projectName: [framework-core]
 * @author: [Sir丶雨轩]
 * @createDate: [2019年4月28日 下午8:41:47]
 * @version: [v1.0]
 */
public class RespEntity extends JSONObject {

    private static final long serialVersionUID = 1L;

    /**
     * 请求失败返回
     */
    public static final RespEntity FAIL = new RespEntity().setCode(HttpCode.FAIL).setMsg("服务器异常,请稍后重试...");
    /**
     * 请求成功返回
     */
    public static final RespEntity SUCCESS = new RespEntity().setCode(HttpCode.SUCCESS);

    @SuppressWarnings("unused")
    private HttpCode code;
    @SuppressWarnings("unused")
    private Object data;
    @SuppressWarnings("unused")
    private String msg;

    public RespEntity() {
    }

    public RespEntity setCode(HttpCode code) {
        this.put("code", code.value());
        return this;
    }

    public int getCode() {
        return this.getIntValue("code");
    }

    public RespEntity setData(Object data) {
        this.put("data", data);
        return this;
    }

    public <T> T getData(Class<T> cls) {
        return this.getObject("data", cls);
    }

    public Object getData() {
        return this.get("data");
    }

    public RespEntity setMsg(String msg) {
        this.put("msg", msg);
        return this;
    }

    public String getMsg() {
        return this.getString("msg");
    }

    public static RespEntity resMsg(HttpCode code, String msg) {
        return new RespEntity().setCode(code).setMsg(msg);
    }

    public static RespEntity resMsg(HttpCode code, String msg, Object data) {
        return new RespEntity().setCode(code).setMsg(msg).setData(data);
    }

    public static RespEntity resMsg(HttpCode code, Object data) {
        return resMsg(code, "success", data);
    }

    public static RespEntity success(Object data) {
        return resMsg(HttpCode.SUCCESS, data);
    }


    public static RespEntity fail(String msg) {
        return resMsg(HttpCode.FAIL, msg);
    }

    public static RespEntity fail(String msg, Object... str) {
        return resMsg(HttpCode.FAIL, StrUtil.format(msg, str));
    }

    /**
     * 为了兼容旧版本 不进行删除,禁止使用
     *
     * @return
     */
    @Deprecated
    public static RespEntity success() {
        return SUCCESS;
    }

    /**
     * 为了兼容旧版本 不进行删除,禁止使用
     *
     * @return
     */
    @Deprecated
    public static RespEntity fail() {
        return FAIL;
    }

    public RespEntity put(String key, Object v) {
        super.put(key, v);
        return this;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}