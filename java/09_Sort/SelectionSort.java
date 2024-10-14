package indi.shensju.sort;

import java.util.Arrays;

/**
 * @author shensju
 * @date 2024/10/13 21:55
 * 选择排序算法
 *     - 时间复杂度：最好 O(n^2)，最坏 O(n^2)，平均 O(n^2)
 *     - 空间复杂度：O(1)
 *     - 不稳定的排序算法
 */
public class SelectionSort {

    public static void main(String[] args) {
        int[] a = {4, 5, 6, 3, 2, 1};
        System.out.println("初始数据：" + Arrays.toString(a));
        selectionSort(a, a.length);
        System.out.println("经过选择排序后的数据：" + Arrays.toString(a));
    }

    public static void selectionSort(int[] a, int n) {
        for (int i = 0; i < n; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (a[j] < a[min]) {
                    min = j;
                }
            }
            int tmp = a[i];
            a[i] = a[min];
            a[min] = tmp;
        }
    }
}
