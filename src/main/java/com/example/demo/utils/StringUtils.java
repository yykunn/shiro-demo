package com.example.demo.utils;

public class StringUtils {
    /**
     * str != null && !"".equals(str.trim()) && !"null".equals(str.trim())
     * @param str
     * @return
     */
    public static boolean check(String str) {
        return str != null && !"".equals(str.trim()) && !"null".equals(str.trim());
    }
}
