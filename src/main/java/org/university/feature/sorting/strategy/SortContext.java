package org.university.feature.sorting.strategy;

public class SortContext {
    private SortStrategy strategy;

    public void setStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void sort(int[] array) {
        if (strategy == null) {
            throw new IllegalStateException("SortStrategy is not set");
        }
        strategy.sort(array);
    }
}
