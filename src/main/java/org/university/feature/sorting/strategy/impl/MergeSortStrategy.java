package org.university.feature.sorting.strategy.impl;

import strategy.SortStrategy;
import java.util.Arrays;

public class MergeSortStrategy implements SortStrategy {

    @Override
    public void sort(int[] array) {
        mergeSort(array, 0, array.length - 1);
    }

    private void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            mergeSort(arr, left, middle);
            mergeSort(arr, middle + 1, right);
            merge(arr, left, middle, right);
        }
    }

    private void merge(int[] arr, int left, int middle, int right) {
        int[] leftArr = Arrays.copyOfRange(arr, left, middle + 1);
        int[] rightArr = Arrays.copyOfRange(arr, middle + 1, right + 1);

        int i = 0, j = 0, k = left;

        while (i < leftArr.length && j < rightArr.length) {
            arr[k++] = leftArr[i] <= rightArr[j]
                    ? leftArr[i++]
                    : rightArr[j++];
        }

        while (i < leftArr.length) {
            arr[k++] = leftArr[i++];
        }

        while (j < rightArr.length) {
            arr[k++] = rightArr[j++];
        }
    }
}
