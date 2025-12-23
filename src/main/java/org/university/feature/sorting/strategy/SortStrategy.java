package org.university.feature.sorting.strategy;

import org.university.common.collection.CustomList;

import java.util.Comparator;

public interface SortStrategy {
    <T> void sort(CustomList<T> list, Comparator<T> comparator);

    default <T extends Comparable<T>> void sort(CustomList<T> list) {
        sort(list, Comparator.naturalOrder());
    }

    String getName();
}

