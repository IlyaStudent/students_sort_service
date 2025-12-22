package org.university.feature.sorting.strategy.impl;

import strategy.SortStrategy;
import java.util.Arrays;

public class EvenOddMergeSort implements SortStrategy {

    @Override
    public void sort(int[] array) {
        sortPart(array, 0);
        sortPart(array, 1);
    }

    private void sortPart(int[] array, int start) {
        int[] part = extract(array, start);
        Arrays.sort(part); // merge-based implementation internally
        insert(array, part, start);
    }

    private int[] extract(int[] a, int start) {
        int size = (a.length + 1 - start) / 2;
        int[] result = new int[size];
        for (int i = 0, j = start; j < a.length; j += 2) {
            result[i++] = a[j];
        }
        return result;
    }

    private void insert(int[] a, int[] part, int start) {
        for (int i = 0, j = start; j < a.length; j += 2) {
            a[j] = part[i++];
        }
    }
}
