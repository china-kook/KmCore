package com.km66.framework.core.transaction;

import cn.hutool.core.util.StrUtil;

public interface TransactionConfig {

    class PointcutExpression {
        private StringBuffer stringBuffer = new StringBuffer();

        public void set(String str) {
            stringBuffer.append(str);
        }
        public String get(){
            return stringBuffer.toString();
        }
    }

    /**
     * 设置切面内容
     * @param pointcutExpression
     */
    default void setPointcutExpression(PointcutExpression pointcutExpression) {
    }
}
