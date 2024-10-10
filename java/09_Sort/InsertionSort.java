package indi.shensju.sort;

import java.util.Arrays;

/**
 * @author shensju
 * @date 2024/10/10 23:00
 * 插入排序算法
 *     - 时间复杂度：最好 O(n)，最坏 O(n^2)，平均 O(n^2)
 *     - 空间复杂度：O(1)
 *     - 稳定的排序算法
 */
public class InsertionSort {

    public static void main(String[] args) {
        int[] a = {4, 5, 6, 1, 3, 2};
        System.out.println("初始数据：" + Arrays.toString(a));
        insertionSort(a, 6);
        System.out.println("经过插入排序后的数据：" + Arrays.toString(a));
    }

    public static void insertionSort(int[] a, int n) {
        if (n <= 1) return;
        for (int i = 1; i < n; i++) {
            int value = a[i];
            int j = i - 1;
            // 查找插入的位置
            for (; j >= 0; j--) {
                if (a[j] > value) {
                    a[j+1] = a[j]; // 数据移动
                } else {
                    break;
                }
            }
            a[j+1] = value; // 插入数据
        }
    }
}
