package com.hkk.demo.kt;

public class HeapSort {

    public int[] sortArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return nums;
        }
        quickSort(nums, 0, nums.length - 1);
        return nums;
    }

    void quickSort(int[] nums, int start, int end) {
        if (start < end) {
            int middleIndex = partition(nums, start, end);
            quickSort(nums, 0, middleIndex - 1);
            quickSort(nums, middleIndex + 1, end);
        }
    }

    int partition(int[] nums, int start, int end) {
        // 相当于使用end作为枢纽字段
        int i = start, j = start;
        // i 之前的值都是小于枢纽的值
        // j 向右遍历，将小于枢纽的值移到前面
        while (j < end) {
            if (nums[j] < nums[end]) {
                swap(nums, i++, j);
            }
            j++;
        }
        swap(nums, i, end);
        return i;
    }

    void swap(int[] nums, int i, int j) {
        if (i == j) {
            return;
        }
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }
}
