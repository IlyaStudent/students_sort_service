package org.university.feature.sorting.strategy.impl;

import org.junit.jupiter.api.Test;
import org.university.feature.sorting.strategy.SortStrategy;
import org.university.feature.sorting.strategy.impl.even_odd_sorts.EvenOddBubbleSort;
import org.university.feature.sorting.strategy.impl.even_odd_sorts.EvenOddMergeSort;
import org.university.feature.sorting.strategy.impl.even_odd_sorts.EvenOddQuickSort;
import org.university.feature.sorting.strategy.impl.usual_sorts.BubbleSortStrategy;
import org.university.feature.sorting.strategy.impl.usual_sorts.MergeSortStrategy;
import org.university.feature.sorting.strategy.impl.usual_sorts.QuickSortStrategy;
import org.university.feature.ui.option.GeneralSortAlgorithmOption;
import org.university.feature.ui.option.SpecificSortOption;

import static org.junit.jupiter.api.Assertions.*;

class SortFactoryTest {

    @Test
    void getSortStrategyFromOptions_ShouldReturnBubbleSort_ForSortAllFields() {
        SortStrategy strategy = SortFactory.getSortStrategyFromOptions(
                GeneralSortAlgorithmOption.BUBBLE_SORT,
                SpecificSortOption.SORT_ALL_FIELDS
        );

        assertNotNull(strategy);
        assertInstanceOf(BubbleSortStrategy.class, strategy);
        assertEquals("Пузырьковая сортировка", strategy.getName());
    }

    @Test
    void getSortStrategyFromOptions_ShouldReturnMergeSort_ForSortAllFields() {
        SortStrategy strategy = SortFactory.getSortStrategyFromOptions(
                GeneralSortAlgorithmOption.MERGE_SORT,
                SpecificSortOption.SORT_ALL_FIELDS
        );

        assertNotNull(strategy);
        assertInstanceOf(MergeSortStrategy.class, strategy);
        assertEquals("Сортировка слиянием", strategy.getName());
    }

    @Test
    void getSortStrategyFromOptions_ShouldReturnQuickSort_ForSortAllFields() {
        SortStrategy strategy = SortFactory.getSortStrategyFromOptions(
                GeneralSortAlgorithmOption.QUICK_SORT,
                SpecificSortOption.SORT_ALL_FIELDS
        );

        assertNotNull(strategy);
        assertInstanceOf(QuickSortStrategy.class, strategy);
        assertEquals("Быстрая сортировка", strategy.getName());
    }

    @Test
    void getSortStrategyFromOptions_ShouldReturnEvenOddBubbleSort_ForSortEvenScore() {
        SortStrategy strategy = SortFactory.getSortStrategyFromOptions(
                GeneralSortAlgorithmOption.BUBBLE_SORT,
                SpecificSortOption.SORT_EVEN_SCORE
        );

        assertNotNull(strategy);
        assertInstanceOf(EvenOddBubbleSort.class, strategy);
        assertEquals("Четно-нечетная пузырьковая сортировка", strategy.getName());
    }

    @Test
    void getSortStrategyFromOptions_ShouldReturnEvenOddMergeSort_ForSortEvenScore() {
        SortStrategy strategy = SortFactory.getSortStrategyFromOptions(
                GeneralSortAlgorithmOption.MERGE_SORT,
                SpecificSortOption.SORT_EVEN_SCORE
        );

        assertNotNull(strategy);
        assertInstanceOf(EvenOddMergeSort.class, strategy);
        assertEquals("Четно-нечетная сортировка слиянием", strategy.getName());
    }

    @Test
    void getSortStrategyFromOptions_ShouldReturnEvenOddQuickSort_ForSortEvenScore() {
        SortStrategy strategy = SortFactory.getSortStrategyFromOptions(
                GeneralSortAlgorithmOption.QUICK_SORT,
                SpecificSortOption.SORT_EVEN_SCORE
        );

        assertNotNull(strategy);
        assertInstanceOf(EvenOddQuickSort.class, strategy);
        assertEquals("Четно-нечетная быстрая сортировка", strategy.getName());
    }

    @Test
    void getSortStrategyFromOptions_ShouldThrowException_ForBackSpecificOption() {
        assertThrows(IllegalArgumentException.class, () ->
                SortFactory.getSortStrategyFromOptions(
                        GeneralSortAlgorithmOption.BUBBLE_SORT,
                        SpecificSortOption.BACK
                )
        );
    }

    @Test
    void getSortStrategyFromOptions_ShouldThrowException_ForExitSpecificOption() {
        assertThrows(IllegalArgumentException.class, () ->
                SortFactory.getSortStrategyFromOptions(
                        GeneralSortAlgorithmOption.BUBBLE_SORT,
                        SpecificSortOption.EXIT
                )
        );
    }

    @Test
    void getSortStrategyFromOptions_ShouldThrowException_ForBackAlgorithmOption() {
        assertThrows(IllegalArgumentException.class, () ->
                SortFactory.getSortStrategyFromOptions(
                        GeneralSortAlgorithmOption.BACK,
                        SpecificSortOption.SORT_ALL_FIELDS
                )
        );
    }

    @Test
    void getSortStrategyFromOptions_ShouldThrowException_ForExitAlgorithmOption() {
        assertThrows(IllegalArgumentException.class, () ->
                SortFactory.getSortStrategyFromOptions(
                        GeneralSortAlgorithmOption.EXIT,
                        SpecificSortOption.SORT_ALL_FIELDS
                )
        );
    }

    @Test
    void getSortStrategyFromOptions_ShouldReturnDifferentStrategies_ForDifferentSpecificOptions() {
        SortStrategy basicBubble = SortFactory.getSortStrategyFromOptions(
                GeneralSortAlgorithmOption.BUBBLE_SORT,
                SpecificSortOption.SORT_ALL_FIELDS
        );
        SortStrategy evenOddBubble = SortFactory.getSortStrategyFromOptions(
                GeneralSortAlgorithmOption.BUBBLE_SORT,
                SpecificSortOption.SORT_EVEN_SCORE
        );

        assertNotSame(basicBubble, evenOddBubble);
        assertInstanceOf(BubbleSortStrategy.class, basicBubble);
        assertInstanceOf(EvenOddBubbleSort.class, evenOddBubble);
    }

    @Test
    void getSortStrategyFromOptions_ShouldReturnSameInstance_ForSameOptions() {
        SortStrategy strategy1 = SortFactory.getSortStrategyFromOptions(
                GeneralSortAlgorithmOption.BUBBLE_SORT,
                SpecificSortOption.SORT_ALL_FIELDS
        );
        SortStrategy strategy2 = SortFactory.getSortStrategyFromOptions(
                GeneralSortAlgorithmOption.BUBBLE_SORT,
                SpecificSortOption.SORT_ALL_FIELDS
        );

        assertSame(strategy1, strategy2, "Factory should return the same instance for the same options");
    }

    @Test
    void getSortStrategyFromOptions_ShouldHandleAllCombinations() {
        GeneralSortAlgorithmOption[] algorithms = {
                GeneralSortAlgorithmOption.BUBBLE_SORT,
                GeneralSortAlgorithmOption.MERGE_SORT,
                GeneralSortAlgorithmOption.QUICK_SORT
        };

        SpecificSortOption[] specifics = {
                SpecificSortOption.SORT_ALL_FIELDS,
                SpecificSortOption.SORT_EVEN_SCORE
        };

        for (GeneralSortAlgorithmOption algorithm : algorithms) {
            for (SpecificSortOption specific : specifics) {
                SortStrategy strategy = SortFactory.getSortStrategyFromOptions(algorithm, specific);
                assertNotNull(strategy, "Strategy should not be null for " + algorithm + " and " + specific);
                assertNotNull(strategy.getName(), "Strategy name should not be null");
            }
        }
    }
}
