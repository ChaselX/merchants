package com.chasel.passbook.merchants.security;

/**
 * <h1>用 ThreadLocal 去单独存储每一个线程携带的 Token 信息</h1>
 *
 * @author XieLongzhen
 * @date 2019/3/4 16:38
 */
public class AccessContext {
    private static final ThreadLocal<String> token = new ThreadLocal<>();

    public static String getToken() {
        return token.get();
    }

    public static void setToken(String tokenStr) {
        token.set(tokenStr);
    }

    public static void clearAccessKey() {
        token.remove();
    }
}
