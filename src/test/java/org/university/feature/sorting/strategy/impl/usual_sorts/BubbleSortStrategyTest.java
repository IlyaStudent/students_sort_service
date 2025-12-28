package org.university.feature.sorting.strategy.impl.usual_sorts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.university.common.collection.CustomArrayList;
import org.university.common.collection.CustomList;
import org.university.common.model.Student;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class BubbleSortStrategyTest {

    private BubbleSortStrategy bubbleSort;

    @BeforeEach
    void setUp() {
        bubbleSort = new BubbleSortStrategy();
    }

    @Test
    void getName_ShouldReturnBubbleSortName() {
        assertEquals("Пузырьковая сортировка", bubbleSort.getName());
    }

    @Test
    void sort_ShouldThrowException_WhenComparatorIsNull() {
        CustomList<Integer> list = new CustomArrayList<>();
        assertThrows(IllegalArgumentException.class, () ->
                bubbleSort.sort(list, null)
        );
    }

    @Test
    void sort_ShouldHandleEmptyList() {
        CustomList<Integer> list = new CustomArrayList<>();
        Comparator<Integer> comparator = Comparator.naturalOrder();

        assertDoesNotThrow(() -> bubbleSort.sort(list, comparator));
        assertEquals(0, list.size());
    }

    @Test
    void sort_ShouldHandleSingleElement() {
        CustomList<Integer> list = new CustomArrayList<>();
        list.add(42);
        Comparator<Integer> comparator = Comparator.naturalOrder();

        bubbleSort.sort(list, comparator);

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

        bubbleSort.sort(list, Comparator.naturalOrder());

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

        bubbleSort.sort(list, Comparator.reverseOrder());

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

        bubbleSort.sort(list, Comparator.naturalOrder());

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
        assertEquals(4, list.get(3));
        assertEquals(5, list.get(4));
    }

    @Test
    void sort_ShouldHandleReverseSortedList() {
        CustomList<Integer> list = new CustomArrayList<>();
        list.add(5);
        list.add(4);
        list.add(3);
        list.add(2);
        list.add(1);

        bubbleSort.sort(list, Comparator.naturalOrder());

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

        bubbleSort.sort(list, Comparator.naturalOrder());

        assertEquals(1, list.get(0));
        assertEquals(1, list.get(1));
        assertEquals(2, list.get(2));
        assertEquals(3, list.get(3));
        assertEquals(3, list.get(4));
    }

    @Test
    void sort_ShouldSortStrings() {
        CustomList<String> list = new CustomArrayList<>();
        list.add("Zebra");
        list.add("Apple");
        list.add("Mango");
        list.add("Banana");

        bubbleSort.sort(list, Comparator.naturalOrder());

        assertEquals("Apple", list.get(0));
        assertEquals("Banana", list.get(1));
        assertEquals("Mango", list.get(2));
        assertEquals("Zebra", list.get(3));
    }

    @Test
    void sort_ShouldSortStudentsByGroupNumber() {
        CustomList<Student> students = new CustomArrayList<>();
        students.add(new Student.Builder().groupNumber("CS-103").build());
        students.add(new Student.Builder().groupNumber("CS-101").build());
        students.add(new Student.Builder().groupNumber("CS-102").build());

        bubbleSort.sort(students, Comparator.comparing(Student::getGroupNumber));

        assertEquals("CS-101", students.get(0).getGroupNumber());
        assertEquals("CS-102", students.get(1).getGroupNumber());
        assertEquals("CS-103", students.get(2).getGroupNumber());
    }

    @Test
    void sort_ShouldSortStudentsByAverageScore() {
        CustomList<Student> students = new CustomArrayList<>();
        students.add(new Student.Builder().averageScore(4.5).build());
        students.add(new Student.Builder().averageScore(3.0).build());
        students.add(new Student.Builder().averageScore(5.0).build());

        bubbleSort.sort(students, Comparator.comparingDouble(Student::getAverageScore));

        assertEquals(3.0, students.get(0).getAverageScore());
        assertEquals(4.5, students.get(1).getAverageScore());
        assertEquals(5.0, students.get(2).getAverageScore());
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
        students.add(new Student.Builder()
                .groupNumber("CS-101")
                .averageScore(3.0)
                .recordBookNumber("2023-00003")
                .build());

        bubbleSort.sort(students, Comparator.naturalOrder());

        assertEquals("CS-101", students.get(0).getGroupNumber());
        assertEquals(3.0, students.get(0).getAverageScore());
        assertEquals("CS-101", students.get(1).getGroupNumber());
        assertEquals(5.0, students.get(1).getAverageScore());
        assertEquals("CS-102", students.get(2).getGroupNumber());
    }

    @Test
    void sort_ShouldHandleLargeList() {
        CustomList<Integer> list = new CustomArrayList<>();
        for (int i = 1000; i > 0; i--) {
            list.add(i);
        }

        bubbleSort.sort(list, Comparator.naturalOrder());

        for (int i = 0; i < 1000; i++) {
            assertEquals(i + 1, list.get(i));
        }
    }

    @Test
    void sort_ShouldBeStable() {
        CustomList<Student> students = new CustomArrayList<>();
        Student s1 = new Student.Builder()
                .groupNumber("CS-101")
                .recordBookNumber("2023-00001")
                .build();
        Student s2 = new Student.Builder()
                .groupNumber("CS-101")
                .recordBookNumber("2023-00002")
                .build();
        Student s3 = new Student.Builder()
                .groupNumber("CS-101")
                .recordBookNumber("2023-00003")
                .build();

        students.add(s1);
        students.add(s2);
        students.add(s3);

        bubbleSort.sort(students, Comparator.comparing(Student::getGroupNumber));

        assertEquals("2023-00001", students.get(0).getRecordBookNumber());
        assertEquals("2023-00002", students.get(1).getRecordBookNumber());
        assertEquals("2023-00003", students.get(2).getRecordBookNumber());
    }
}
