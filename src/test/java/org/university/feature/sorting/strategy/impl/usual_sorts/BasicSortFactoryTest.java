package org.university.feature.sorting.strategy.impl.usual_sorts;

import org.junit.jupiter.api.Test;
import org.university.feature.sorting.strategy.SortStrategy;
import org.university.feature.ui.option.GeneralSortAlgorithmOption;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class BasicSortFactoryTest {

    @Test
    void constructor_ShouldThrowAssertionError() {
        try {
            Constructor<BasicSortFactory> constructor = BasicSortFactory.class.getDeclaredConstructor();
            constructor.setAccessible(true);

            InvocationTargetException exception = assertThrows(
                    InvocationTargetException.class,
                    constructor::newInstance
            );

            assertInstanceOf(AssertionError.class, exception.getCause());
        } catch (NoSuchMethodException e) {
            fail("Constructor should exist");
        }
    }

    @Test
    void getInstanceFromAlgorithmOption_ShouldReturnBubbleSortStrategy() {
        SortStrategy strategy = BasicSortFactory.getInstanceFromAlgorithmOption(
                GeneralSortAlgorithmOption.BUBBLE_SORT
        );

        assertNotNull(strategy);
        assertInstanceOf(BubbleSortStrategy.class, strategy);
        assertEquals("Пузырьковая сортировка", strategy.getName());
    }

    @Test
    void getInstanceFromAlgorithmOption_ShouldReturnMergeSortStrategy() {
        SortStrategy strategy = BasicSortFactory.getInstanceFromAlgorithmOption(
                GeneralSortAlgorithmOption.MERGE_SORT
        );

        assertNotNull(strategy);
        assertInstanceOf(MergeSortStrategy.class, strategy);
        assertEquals("Сортировка слиянием", strategy.getName());
    }

    @Test
    void getInstanceFromAlgorithmOption_ShouldReturnQuickSortStrategy() {
        SortStrategy strategy = BasicSortFactory.getInstanceFromAlgorithmOption(
                GeneralSortAlgorithmOption.QUICK_SORT
        );

        assertNotNull(strategy);
        assertInstanceOf(QuickSortStrategy.class, strategy);
        assertEquals("Быстрая сортировка", strategy.getName());
    }

    @Test
    void getInstanceFromAlgorithmOption_ShouldThrowException_ForBackOption() {
        assertThrows(IllegalArgumentException.class, () ->
                BasicSortFactory.getInstanceFromAlgorithmOption(GeneralSortAlgorithmOption.BACK)
        );
    }

    @Test
    void getInstanceFromAlgorithmOption_ShouldThrowException_ForExitOption() {
        assertThrows(IllegalArgumentException.class, () ->
                BasicSortFactory.getInstanceFromAlgorithmOption(GeneralSortAlgorithmOption.EXIT)
        );
    }

    @Test
    void getInstanceFromAlgorithmOption_ShouldReturnSameInstanceForSameOption() {
        SortStrategy strategy1 = BasicSortFactory.getInstanceFromAlgorithmOption(
                GeneralSortAlgorithmOption.BUBBLE_SORT
        );
        SortStrategy strategy2 = BasicSortFactory.getInstanceFromAlgorithmOption(
                GeneralSortAlgorithmOption.BUBBLE_SORT
        );

        assertSame(strategy1, strategy2, "Factory should return the same instance for the same option");
    }

    @Test
    void getInstanceFromAlgorithmOption_ShouldReturnDifferentInstancesForDifferentOptions() {
        SortStrategy bubbleSort = BasicSortFactory.getInstanceFromAlgorithmOption(
                GeneralSortAlgorithmOption.BUBBLE_SORT
        );
        SortStrategy mergeSort = BasicSortFactory.getInstanceFromAlgorithmOption(
                GeneralSortAlgorithmOption.MERGE_SORT
        );
        SortStrategy quickSort = BasicSortFactory.getInstanceFromAlgorithmOption(
                GeneralSortAlgorithmOption.QUICK_SORT
        );

        assertNotSame(bubbleSort, mergeSort);
        assertNotSame(bubbleSort, quickSort);
        assertNotSame(mergeSort, quickSort);
    }

    @Test
    void getInstanceFromAlgorithmOption_ShouldReturnWorkingStrategies() {
        SortStrategy bubbleSort = BasicSortFactory.getInstanceFromAlgorithmOption(
                GeneralSortAlgorithmOption.BUBBLE_SORT
        );
        SortStrategy mergeSort = BasicSortFactory.getInstanceFromAlgorithmOption(
                GeneralSortAlgorithmOption.MERGE_SORT
        );
        SortStrategy quickSort = BasicSortFactory.getInstanceFromAlgorithmOption(
                GeneralSortAlgorithmOption.QUICK_SORT
        );

        assertNotNull(bubbleSort.getName());
        assertNotNull(mergeSort.getName());
        assertNotNull(quickSort.getName());
    }
}
