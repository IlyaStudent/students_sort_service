package org.university.feature.sorting.strategy.impl;

import org.university.feature.sorting.strategy.SortStrategy;
import org.university.feature.sorting.strategy.impl.even_odd_sorts.EvenOddSortFactory;
import org.university.feature.sorting.strategy.impl.usual_sorts.BasicSortFactory;
import org.university.feature.ui.option.GeneralSortAlgorithmOption;
import org.university.feature.ui.option.SpecificSortOption;

public final class SortFactory {

    private SortFactory() {}

    public static SortStrategy getSortStrategyFromOptions(
            GeneralSortAlgorithmOption algorithmOption, SpecificSortOption specificSortOption) {

        return switch (specificSortOption) {
            case SORT_ALL_FIELDS ->
                    BasicSortFactory.getInstanceFromAlgorithmOption(algorithmOption);
            case SORT_ALL_EVEN_FIELDS ->
                    EvenOddSortFactory.getInstanceFromAlgorithmOption(algorithmOption);
            default ->
                    throw new IllegalArgumentException(
                            "Specific sort option does not exists: " + specificSortOption);
        };
    }
}
