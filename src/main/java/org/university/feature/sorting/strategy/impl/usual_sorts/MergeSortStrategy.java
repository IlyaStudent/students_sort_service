package org.university.feature.sorting.strategy.impl.usual_sorts;

import org.university.common.Constants;
import org.university.common.collection.CustomArrayList;
import org.university.common.collection.CustomList;
import org.university.feature.sorting.strategy.SortStrategy;

import java.util.Comparator;

public class MergeSortStrategy implements SortStrategy {

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

        mergeSort(list, 0, list.size() - 1, comparator);
    }

    private <T> void mergeSort(CustomList<T> list, int left, int right, Comparator<T> comparator) {
        if (left < right) {
            int middle = left + (right - left) / 2;

            mergeSort(list, left, middle, comparator);
            mergeSort(list, middle + 1, right, comparator);

            merge(list, left, middle, right, comparator);
        }
    }

    private <T> void merge(CustomList<T> list, int left, int middle, int right, Comparator<T> comparator) {
        CustomList<T> leftList = new CustomArrayList<>();
        CustomList<T> rightList = new CustomArrayList<>();

        for (int i = left; i <= middle; i++) {
            leftList.add(list.get(i));
        }
        for (int i = middle + 1; i <= right; i++) {
            rightList.add(list.get(i));
        }

        int i = 0;
        int j = 0;
        int k = left;

        while (i < leftList.size() && j < rightList.size()) {
            if (comparator.compare(leftList.get(i), rightList.get(j)) <= 0) {
                list.set(k, leftList.get(i));
                i++;
            } else {
                list.set(k, rightList.get(j));
                j++;
            }
            k++;
        }

        while (i < leftList.size()) {
            list.set(k, leftList.get(i));
            i++;
            k++;
        }

        while (j < rightList.size()) {
            list.set(k, rightList.get(j));
            j++;
            k++;
        }
    }

    @Override
    public String getName() {
        return Constants.MERGE_SORT_NAME;
    }
}
