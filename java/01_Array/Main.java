package com.shensju.array;

/**
 * @Author: shensju
 * @Date: 2021/1/27 22:50
 */
public class Main {

    public static void main(String[] args) {
        Array<Integer> arr = new Array<>(); // 数组初始容量大小为10个元素
        for (int i = 0; i < 10; i++)
            arr.addLast(i);
        System.out.println(arr);

        arr.add(1, 100);
        System.out.println(arr);

        arr.addFirst(-1);
        System.out.println(arr);

        arr.remove(2);
        System.out.println(arr);

        arr.removeElement(4);
        System.out.println(arr);

        arr.removeFirst();
        System.out.println(arr);
    }
}

