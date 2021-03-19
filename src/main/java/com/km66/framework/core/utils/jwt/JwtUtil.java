package com.km66.framework.core.utils.jwt;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.km66.framework.core.basis.BaseEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

@Slf4j
public class JwtUtil {
    private static final String SECRET = "XX#$%()(#*!()!KL<><MQLMNQNQJQK sdfkjsdrow32234545fdf>?N<:{LWPW";


    /**
     * 根据传入的实体生成JWT密钥
     *
     * @param entity
     * @return
     */
    public static String get(BaseEntity entity) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .setClaims(JSONObject.parseObject(JSONObject.toJSONString(entity)))
                .signWith(signatureAlgorithm, SECRET.getBytes())
                .setExpiration(DateUtil.offsetDay(new Date(), 1));
        return builder.compact();
    }

    public static <T> T pares(String token, Class<? extends BaseEntity> clazz) {
        Object object = null;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJws(token).getBody();
            object = clazz.newInstance();
            clazz.getMethod("putAll", Map.class).invoke(object, claims);
            return (T) object;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | RuntimeException e) {
            log.error("JWT解析失败 {}", e.getMessage());
            return null;
        }
    }


}
