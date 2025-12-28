package org.university.feature.sorting.strategy.impl.usual_sorts;

import org.university.common.Constants;
import org.university.common.collection.CustomList;
import org.university.feature.sorting.strategy.SortStrategy;

import java.util.Comparator;

public class BubbleSortStrategy implements SortStrategy {

    @Override
    public <T> void sort(CustomList<T> list, Comparator<T> comparator) {
        if (list == null) {
            throw new IllegalArgumentException("List cannot be null");
        }

        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }

        int n = list.size();
        if (n <= 1) {
            return;
        }

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (comparator.compare(list.get(j), list.get(j + 1)) > 0) {
                    swap(list, j, j + 1);
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }
    }

    private <T> void swap(CustomList<T> list, int i, int j) {
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    @Override
    public String getName() {
        return Constants.BUBBLE_SORT_NAME;
    }
}
