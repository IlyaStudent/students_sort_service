package org.university.feature.sorting.strategy.impl.usual_sorts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.university.common.collection.CustomArrayList;
import org.university.common.collection.CustomList;
import org.university.common.model.Student;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class QuickSortStrategyTest {

    private QuickSortStrategy quickSort;

    @BeforeEach
    void setUp() {
        quickSort = new QuickSortStrategy();
    }

    @Test
    void getName_ShouldReturnQuickSortName() {
        assertEquals("Быстрая сортировка", quickSort.getName());
    }

    @Test
    void sort_ShouldThrowException_WhenComparatorIsNull() {
        CustomList<Integer> list = new CustomArrayList<>();
        assertThrows(IllegalArgumentException.class, () ->
                quickSort.sort(list, null)
        );
    }

    @Test
    void sort_ShouldHandleEmptyList() {
        CustomList<Integer> list = new CustomArrayList<>();
        Comparator<Integer> comparator = Comparator.naturalOrder();

        assertDoesNotThrow(() -> quickSort.sort(list, comparator));
        assertEquals(0, list.size());
    }

    @Test
    void sort_ShouldHandleSingleElement() {
        CustomList<Integer> list = new CustomArrayList<>();
        list.add(42);
        Comparator<Integer> comparator = Comparator.naturalOrder();

        quickSort.sort(list, comparator);

        assertEquals(1, list.size());
        assertEquals(42, list.get(0));
    }

    @Test
    void sort_ShouldSortIntegersAscending() {
        CustomList<Integer> list = new CustomArrayList<>();
        list.add(5);
        list.add(2);
        list.add(8);
        list.add(1);
        list.add(9);

        quickSort.sort(list, Comparator.naturalOrder());

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(5, list.get(2));
        assertEquals(8, list.get(3));
        assertEquals(9, list.get(4));
    }

    @Test
    void sort_ShouldSortIntegersDescending() {
        CustomList<Integer> list = new CustomArrayList<>();
        list.add(5);
        list.add(2);
        list.add(8);
        list.add(1);
        list.add(9);

        quickSort.sort(list, Comparator.reverseOrder());

        assertEquals(9, list.get(0));
        assertEquals(8, list.get(1));
        assertEquals(5, list.get(2));
        assertEquals(2, list.get(3));
        assertEquals(1, list.get(4));
    }

    @Test
    void sort_ShouldHandleAlreadySortedList() {
        CustomList<Integer> list = new CustomArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        quickSort.sort(list, Comparator.naturalOrder());

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
        assertEquals(4, list.get(3));
        assertEquals(5, list.get(4));
    }

    @Test
    void sort_ShouldHandleDuplicates() {
        CustomList<Integer> list = new CustomArrayList<>();
        list.add(3);
        list.add(1);
        list.add(3);
        list.add(2);
        list.add(1);

        quickSort.sort(list, Comparator.naturalOrder());

        assertEquals(1, list.get(0));
        assertEquals(1, list.get(1));
        assertEquals(2, list.get(2));
        assertEquals(3, list.get(3));
        assertEquals(3, list.get(4));
    }

    @Test
    void sort_ShouldSortStudentsNaturally() {
        CustomList<Student> students = new CustomArrayList<>();
        students.add(new Student.Builder()
                .groupNumber("CS-102")
                .averageScore(4.0)
                .recordBookNumber("2023-00002")
                .build());
        students.add(new Student.Builder()
                .groupNumber("CS-101")
                .averageScore(5.0)
                .recordBookNumber("2023-00001")
                .build());

        quickSort.sort(students, Comparator.naturalOrder());

        assertEquals("CS-101", students.get(0).getGroupNumber());
        assertEquals("CS-102", students.get(1).getGroupNumber());
    }

    @Test
    void sort_ShouldHandleLargeList() {
        CustomList<Integer> list = new CustomArrayList<>();
        for (int i = 1000; i > 0; i--) {
            list.add(i);
        }

        quickSort.sort(list, Comparator.naturalOrder());

        for (int i = 0; i < 1000; i++) {
            assertEquals(i + 1, list.get(i));
        }
    }
}
