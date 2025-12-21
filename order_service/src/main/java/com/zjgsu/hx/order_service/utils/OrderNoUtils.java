package com.zjgsu.hx.order_service.utils;

public class OrderNoUtils {
    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String base62(long num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(BASE62.charAt((int) (num % 62)));
            num /= 62;
        }
        return sb.reverse().toString();
    }

    public static String generateOrderNo() {
        String code = base62(System.currentTimeMillis());
        return code.substring(code.length() - 6); // 6 位
    }
}
