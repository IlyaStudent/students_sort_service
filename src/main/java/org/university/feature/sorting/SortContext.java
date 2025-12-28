package org.university.feature.sorting;

import org.university.common.collection.CustomList;
import org.university.feature.sorting.strategy.SortStrategy;

import java.util.Comparator;
import java.util.Objects;

public class SortContext {
    private SortStrategy strategy;

    public SortContext() {
    }

    public SortContext(SortStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "Strategy cannot be null");
    }

    public SortStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(SortStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "Strategy cannot be null");
    }

    public <T> void executeSort(CustomList<T> customList, Comparator<T> comparator) {
        if (strategy == null) {
            throw new IllegalStateException("Sort strategy is not set");
        }
        strategy.sort(customList, comparator);
    }

    public <T extends Comparable<T>> void executeSort(CustomList<T> customList) {
        if (strategy == null) {
            throw new IllegalStateException("Sort strategy is not set");
        }
        strategy.sort(customList);
    }

    public String getCurrentStrategyName() {
        return strategy != null ? strategy.getName() : "None";
    }
}
