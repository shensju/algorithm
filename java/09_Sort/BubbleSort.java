package indi.shensju.sort;

import java.util.Arrays;

/**
 * @author shensju
 * @date 2024/10/10 22:32
 * 冒泡排序算法
 *     - 时间复杂度：最好 O(n)，最坏 O(n^2)，平均 O(n^2)
 *     - 空间复杂度：O(1)
 *     - 稳定的排序算法
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] a = {3, 5, 4, 1, 2, 6};
        System.out.println("初始数据：" + Arrays.toString(a));
        bubbleSort(a, a.length);
        System.out.println("经过冒泡排序后的数据：" + Arrays.toString(a));
    }

    public static void bubbleSort(int[] a, int n) {
        if (n <= 1) return;
        for (int i = 0; i < n; i++) {
            boolean flag = false; // 判断是否有数据交换
            for (int j = 0; j < n - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    int tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                    flag = true; // 表示有数据交换
                }
            }
            if (!flag) break; // 没有数据交换，提前退出冒泡循环
        }
    }
}
