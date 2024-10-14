package indi.shensju.sort;

import java.util.Arrays;

/**
 * @author shensju
 * @date 2024/10/13 22:46
 * 归并排序算法
 *     - 时间复杂度：最好 O(nlogn)，最坏 O(nlogn)，平均 O(nlogn)
 *     - 空间复杂度：O(n)
 *     - 稳定的排序算法
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] a = {1, 5, 6, 2, 3, 4};
        System.out.println("初始数据：" + Arrays.toString(a));
        mergeSort(a, a.length);
        System.out.println("经过归并排序后的数据：" + Arrays.toString(a));
    }

    public static void mergeSort(int[] a, int n) {
        msort(a, 0, n - 1);
    }

    private static void msort(int[] a, int start, int end) {
        // 递归终止条件
        if (start >= end) return;
        // 计算中间位置
        int mid = (start + end) / 2;
        // 分治递归
        msort(a, start, mid);
        msort(a, mid + 1, end);
        // 合并
        merge(a, start, mid, end);
    }

    private static void merge(int[] a, int start, int mid, int end) {
        int[] tmp = new int[a.length];
        int i = start;
        int j = mid + 1;
        int k = 0;
        while (i <= mid && j <= end) {
            if (a[i] <= a[j]) {
                tmp[k++] = a[i++];
            } else {
                tmp[k++] = a[j++];
            }
        }
        while (i <= mid) {
            tmp[k++] = a[i++];
        }
        while (j <= end) {
            tmp[k++] = a[j++];
        }
        for (int m = 0; m < k; m++) {
            a[start + m] = tmp[m];
        }
    }
}
