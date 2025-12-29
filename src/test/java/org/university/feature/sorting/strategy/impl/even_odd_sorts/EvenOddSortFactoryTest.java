package org.university.feature.sorting.strategy.impl.even_odd_sorts;

import org.junit.jupiter.api.Test;
import org.university.feature.sorting.strategy.SortStrategy;
import org.university.feature.ui.option.GeneralSortAlgorithmOption;

import static org.junit.jupiter.api.Assertions.*;

class EvenOddSortFactoryTest {

    @Test
    void getInstanceFromAlgorithmOption_ShouldReturnEvenOddBubbleSort() {
        SortStrategy strategy = EvenOddSortFactory.getInstanceFromAlgorithmOption(
                GeneralSortAlgorithmOption.BUBBLE_SORT
        );

        assertNotNull(strategy);
        assertInstanceOf(EvenOddBubbleSort.class, strategy);
        assertEquals("Четно-нечетная пузырьковая сортировка", strategy.getName());
    }

    @Test
    void getInstanceFromAlgorithmOption_ShouldReturnEvenOddMergeSort() {
        SortStrategy strategy = EvenOddSortFactory.getInstanceFromAlgorithmOption(
                GeneralSortAlgorithmOption.MERGE_SORT
        );

        assertNotNull(strategy);
        assertInstanceOf(EvenOddMergeSort.class, strategy);
        assertEquals("Четно-нечетная сортировка слиянием", strategy.getName());
    }

    @Test
    void getInstanceFromAlgorithmOption_ShouldReturnEvenOddQuickSort() {
        SortStrategy strategy = EvenOddSortFactory.getInstanceFromAlgorithmOption(
                GeneralSortAlgorithmOption.QUICK_SORT
        );

        assertNotNull(strategy);
        assertInstanceOf(EvenOddQuickSort.class, strategy);
        assertEquals("Четно-нечетная быстрая сортировка", strategy.getName());
    }

    @Test
    void getInstanceFromAlgorithmOption_ShouldThrowException_ForBackOption() {
        assertThrows(IllegalArgumentException.class, () ->
                EvenOddSortFactory.getInstanceFromAlgorithmOption(GeneralSortAlgorithmOption.BACK)
        );
    }

    @Test
    void getInstanceFromAlgorithmOption_ShouldThrowException_ForExitOption() {
        assertThrows(IllegalArgumentException.class, () ->
                EvenOddSortFactory.getInstanceFromAlgorithmOption(GeneralSortAlgorithmOption.EXIT)
        );
    }

    @Test
    void getInstanceFromAlgorithmOption_ShouldReturnSameInstanceForSameOption() {
        SortStrategy strategy1 = EvenOddSortFactory.getInstanceFromAlgorithmOption(
                GeneralSortAlgorithmOption.BUBBLE_SORT
        );
        SortStrategy strategy2 = EvenOddSortFactory.getInstanceFromAlgorithmOption(
                GeneralSortAlgorithmOption.BUBBLE_SORT
        );

        assertSame(strategy1, strategy2, "Factory should return the same instance for the same option");
    }

    @Test
    void getInstanceFromAlgorithmOption_ShouldReturnDifferentInstancesForDifferentOptions() {
        SortStrategy bubbleSort = EvenOddSortFactory.getInstanceFromAlgorithmOption(
                GeneralSortAlgorithmOption.BUBBLE_SORT
        );
        SortStrategy mergeSort = EvenOddSortFactory.getInstanceFromAlgorithmOption(
                GeneralSortAlgorithmOption.MERGE_SORT
        );
        SortStrategy quickSort = EvenOddSortFactory.getInstanceFromAlgorithmOption(
                GeneralSortAlgorithmOption.QUICK_SORT
        );

        assertNotSame(bubbleSort, mergeSort);
        assertNotSame(bubbleSort, quickSort);
        assertNotSame(mergeSort, quickSort);
    }

    @Test
    void getInstanceFromAlgorithmOption_ShouldReturnWorkingStrategies() {
        SortStrategy bubbleSort = EvenOddSortFactory.getInstanceFromAlgorithmOption(
                GeneralSortAlgorithmOption.BUBBLE_SORT
        );
        SortStrategy mergeSort = EvenOddSortFactory.getInstanceFromAlgorithmOption(
                GeneralSortAlgorithmOption.MERGE_SORT
        );
        SortStrategy quickSort = EvenOddSortFactory.getInstanceFromAlgorithmOption(
                GeneralSortAlgorithmOption.QUICK_SORT
        );

        assertNotNull(bubbleSort.getName());
        assertNotNull(mergeSort.getName());
        assertNotNull(quickSort.getName());
    }
}
