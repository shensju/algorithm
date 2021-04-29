package com.shensju.string;

import java.util.Arrays;

/**
 * @Author: shensju
 * @Date: 2021/4/29 23:23
 */
public class KMP {

    public static int[] getNext(String str) {
        int[] next = new int[str.length()];
        next[0] = 0;
        for (int i = 1, j = 0; i < str.length(); i++) {
            while (j > 0 && str.charAt(i) != str.charAt(j))
                j = next[j - 1];
            if (str.charAt(i) == str.charAt(j))
                ++j;
            next[i] = j;
        }
        return next;
    }

    public static int index(String str1, String str2, int pos) {
        int[] next = getNext(str2);
        for (int i = pos, j = 0; i < str1.length(); i++) {
            while (j > 0 && str1.charAt(i) != str2.charAt(j))
                j = next[j - 1];
            if (str1.charAt(i) == str2.charAt(j))
                ++j;
            if (j == str2.length())
                return i - j + 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        String str1 = "BBC ABCDAB ABCDABCDABDE";
        String str2 = "ABCDABD";
        int[] next = getNext("ABCDABD");
        System.out.println("next=" + Arrays.toString(next));
        int index = index(str1, str2, 0);
        System.out.println("index=" + index);
    }
}
