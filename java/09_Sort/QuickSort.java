package indi.shensju.sort;

import java.util.Arrays;

/**
 * @author shensju
 * @date 2024/10/13 23:11
 * 快速排序算法
 *     - 时间复杂度：最好 O(nlogn), 最坏 O(n^2)，平均 O(nlogn)
 *     - 空间复杂度：O(1)
 *     - 不稳定的排序算法
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] a = {8, 10, 2, 3, 6, 1, 5};
        System.out.println("初始数据：" + Arrays.toString(a));
        quickSort(a, a.length);
        System.out.println("经过快速排序后的数据：" + Arrays.toString(a));
    }

    public static void quickSort(int[] a, int n) {
        qsort(a, 0, n - 1);
    }

    private static void qsort(int[] a, int start, int end) {
        // 递归终止条件
        if (start >= end) return;
        // 获取分区点
        int q = partition(a, start, end);
        // 分治递归
        qsort(a, start, q - 1);
        qsort(a, q + 1, end);
    }

    private static int partition(int[] a, int start, int end) {
        int pivot = a[end];
        int i = start;
        for (int j = start; j < end; j++) {
            if (a[j] < pivot) {
                int tmp = a[i];
                a[i] = a[j];
                a[j] = tmp;
                i++;
            }
        }
        int tmp = a[i];
        a[i] = pivot;
        a[end] = tmp;
        return i;
    }
}
