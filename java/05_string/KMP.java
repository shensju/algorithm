package com.shensju.string;

import java.util.Arrays;

/**
 * @Author: shensju
 * @Date: 2021/4/29 23:23
 */
public class KMP {

    /**
     * 通过计算返回一个字符串的next数组
     * @param str 待计算的字符串
     * @return next数组
     */
    public static int[] getNext(String str) {
        // 创建一个字符串长度的next数组
        int[] next = new int[str.length()];
        // 默认next数组索引位置为0处的值为0
        next[0] = 0;
        for (int i = 1, j = 0; i < str.length(); i++) {
            // i表示后缀的单个字符的索引值，j表示前缀的单个字符的索引值
            while (j > 0 && str.charAt(i) != str.charAt(j))
                j = next[j - 1];
            if (str.charAt(i) == str.charAt(j))
                ++j;
            next[i] = j;
        }
        return next;
    }

    /**
     * 返回子串在主串中第pos个字符后的索引位置
     * @param primaryString 主串
     * @param subString 子串
     * @param pos 从主串中的第pos个字符后开始匹配
     * @return 如果匹配成功，则返回相应的索引位置；如果匹配失败，则返回-1
     */
    public static int index(String primaryString, String subString, int pos) {
        int[] next = getNext(subString);
        for (int i = pos, j = 0; i < primaryString.length(); i++) {
            // 如果两字符不相等，子串指针回退继续匹配
            while (j > 0 && primaryString.charAt(i) != subString.charAt(j))
                j = next[j - 1];
            // 如果两字符相等，则继续匹配
            if (primaryString.charAt(i) == subString.charAt(j))
                ++j;
            if (j == subString.length())
                return i - j + 1;
        }
        return -1;
    }
}