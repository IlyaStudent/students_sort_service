package org.university.feature.sorting.strategy.impl;

import strategy.SortStrategy;

public class EvenOddSelectionSort implements SortStrategy {

    @Override
    public void sort(int[] array) {
        selection(array, 0);
        selection(array, 1);
    }

    private void selection(int[] a, int start) {
        for (int i = start; i < a.length; i += 2) {
            int min = i;
            for (int j = i + 2; j < a.length; j += 2) {
                if (a[j] < a[min]) {
                    min = j;
                }
            }
            int temp = a[i];
            a[i] = a[min];
            a[min] = temp;
        }
    }
}
