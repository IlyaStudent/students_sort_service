package org.university.feature.sorting.strategy;

import org.university.common.Constants;
import org.university.common.collection.CustomList;

import java.util.Comparator;

public class SortContext {
    private SortStrategy strategy;

    public SortContext() {
    }

    public SortContext(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public SortStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public <T> void executeSort(CustomList<T> customList, Comparator<T> comparator) {
        strategy.sort(customList, comparator);
    }

    public <T extends Comparable<T>> void executeSort(CustomList<T> customList) {
        strategy.sort(customList);
    }

    public String getCurrentStrategyName() {
        return strategy != null ? strategy.getName() : Constants.NONE_STRING;
    }
}
