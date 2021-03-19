package com.km66.framework.core.utils.sms;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.km66.framework.core.utils.lang.Lang;
import com.km66.framework.core.utils.sms.impl.YunP;
import org.springframework.lang.NonNull;

/**
 * 短信验证码工具类
 */
public final class SmsUtil {


    private Type type = Type.YUN_P;
    private int len;
    private String code;
    private Mode mode = Mode.REDIS;
    private String phone;

    /**
     * 验证码平台类型
     */
   public enum Type {
        /**
         * 云片
         */
        YUN_P
    }

    /**
     * 存储模式
     */
    public  enum Mode {
        /**
         * Redis存储
         */
        REDIS
    }

    /**
     * 设置验证码类型,目前支持云片
     *
     * @param type 验证码类型
     * @return this
     */
    public SmsUtil type(Type type) {
        this.type = type;
        return this;
    }

    /**
     * 设置验证码长度
     *
     * @param len 长度
     * @return this
     */
    public SmsUtil len(int len) {
        this.len = len;
        return this;
    }

    /**
     * 手动设置验证码
     *
     * @param code 验证码
     * @return this
     */
    public SmsUtil code(String code) {
        this.code = code;
        return this;
    }

    /**
     * 设置验证码缓存模式
     *
     * @param mode 模式
     * @return this
     */
    public SmsUtil mode(Mode mode) {
        this.mode = mode;
        return this;
    }

    /**
     * 设置接收验证码的手机号
     *
     * @param phone 手机号
     * @return this
     */
    public SmsUtil phone(String phone) {
        this.phone = phone;
        return this;
    }

    /**
     * 发送短信验证码
     */
    public void send() {
        //code处理
        String code = this.code;
        if (StrUtil.isBlank(code)) {
            code = RandomUtil.randomNumbers(len < 0 ? 4 : len);
        }
        if (StrUtil.isBlank(phone)) {
            throw new IllegalArgumentException("phone not found");
        }
        if (type == Type.YUN_P) {
            YunP.send(phone, code);
        }

        if(mode == Mode.REDIS){
           // Lang.Redis.setStr(phone, code);
        }
    }

    /**
     * 校验验证码是否正确
     * @param phone 手机号
     * @param code 验证码
     * @return 结果
     */
   // public boolean check(String phone, @NonNull String code) {
    //    return code.equals(Lang.Redis.getStr(phone));
    //}


}
