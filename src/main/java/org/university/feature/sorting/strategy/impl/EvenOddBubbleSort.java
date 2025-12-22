package org.university.feature.sorting.strategy.impl;

import strategy.SortStrategy;

public class EvenOddBubbleSort implements SortStrategy {

    @Override
    public void sort(int[] array) {
        sortByParity(array, 0); // even indices
        sortByParity(array, 1); // odd indices
    }

    private void sortByParity(int[] a, int start) {
        for (int i = start; i < a.length; i += 2) {
            for (int j = start; j < a.length - 2; j += 2) {
                if (a[j] > a[j + 2]) {
                    int temp = a[j];
                    a[j] = a[j + 2];
                    a[j + 2] = temp;
                }
            }
        }
    }
}
