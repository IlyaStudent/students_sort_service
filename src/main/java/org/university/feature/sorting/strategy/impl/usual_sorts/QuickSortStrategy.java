package org.university.feature.sorting.strategy.impl.usual_sorts;

import org.university.common.Constants;
import org.university.common.collection.CustomList;
import org.university.feature.sorting.strategy.SortStrategy;

import java.util.Comparator;

public class QuickSortStrategy implements SortStrategy {

    @Override
    public <T> void sort(CustomList<T> list, Comparator<T> comparator) {
        if (list == null) {
            throw new IllegalArgumentException("List cannot be null");
        }

        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }

        if (list.size() <= 1) {
            return;
        }

        quickSort(list, 0, list.size() - 1, comparator);
    }

    private <T> void quickSort(CustomList<T> list, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int pivotIndex = partition(list, low, high, comparator);

            quickSort(list, low, pivotIndex - 1, comparator);
            quickSort(list, pivotIndex + 1, high, comparator);
        }
    }

    private <T> int partition(CustomList<T> list, int low, int high, Comparator<T> comparator) {
        T pivot = list.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (comparator.compare(list.get(j), pivot) <= 0) {
                i++;
                swap(list, i, j);
            }
        }

        swap(list, i + 1, high);
        return i + 1;
    }

    private <T> void swap(CustomList<T> list, int i, int j) {
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    @Override
    public String getName() {
        return Constants.QUICK_SORT_NAME;
    }
}
