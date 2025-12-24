package org.university.feature.sorting.strategy.impl.usual_sorts;

import org.university.feature.sorting.strategy.SortStrategy;
import org.university.feature.ui.option.GeneralSortAlgorithmOption;

import java.util.HashMap;
import java.util.Map;

public final class BasicSortFactory {
    private static final Map<GeneralSortAlgorithmOption, SortStrategy> sorts = new HashMap<>();

    static {
        initSorts();
    }

    private BasicSortFactory() {}

    private static void initSorts() {
        sorts.put(GeneralSortAlgorithmOption.BUBBLE_SORT, new BubbleSortStrategy());
        sorts.put(GeneralSortAlgorithmOption.MERGE_SORT, new MergeSortStrategy());
        sorts.put(GeneralSortAlgorithmOption.QUICK_SORT, new QuickSortStrategy());
    }

    public static SortStrategy getInstanceFromAlgorithmOption(GeneralSortAlgorithmOption sortOption) {
        if (!sorts.containsKey(sortOption)) {
            throw new IllegalArgumentException("Unsupported algorithm sort option: " + sortOption);
        }

        return sorts.get(sortOption);
    }
}
