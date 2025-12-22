package org.university.feature.sorting.strategy.impl;

import strategy.SortStrategy;

public class EvenOddInsertionSort implements SortStrategy {

    @Override
    public void sort(int[] array) {
        insertion(array, 0);
        insertion(array, 1);
    }

    private void insertion(int[] a, int start) {
        for (int i = start + 2; i < a.length; i += 2) {
            int key = a[i];
            int j = i - 2;

            while (j >= start && a[j] > key) {
                a[j + 2] = a[j];
                j -= 2;
            }
            a[j + 2] = key;
        }
    }
}
