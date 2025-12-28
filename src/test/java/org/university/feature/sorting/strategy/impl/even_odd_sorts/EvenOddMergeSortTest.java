package org.university.feature.sorting.strategy.impl.even_odd_sorts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.university.common.collection.CustomArrayList;
import org.university.common.collection.CustomList;
import org.university.common.model.Student;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class EvenOddMergeSortTest {

    private EvenOddMergeSort evenOddMergeSort;

    @BeforeEach
    void setUp() {
        evenOddMergeSort = new EvenOddMergeSort();
    }

    @Test
    void getName_ShouldReturnEvenOddMergeSortName() {
        assertEquals("ЧетНечет Сортировка слиянием", evenOddMergeSort.getName());
    }

    @Test
    void sort_ShouldThrowException_WhenComparatorIsNull() {
        CustomList<Student> list = new CustomArrayList<>();
        list.add(new Student.Builder().averageScore(4.0).build());
        assertThrows(IllegalArgumentException.class, () ->
                evenOddMergeSort.sort(list, null)
        );
    }

    @Test
    void sort_ShouldThrowException_WhenListContainsNonStudentObjects() {
        CustomList<Integer> list = new CustomArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        assertThrows(IllegalArgumentException.class, () ->
                evenOddMergeSort.sort(list, Comparator.naturalOrder())
        );
    }

    @Test
    void sort_ShouldHandleEmptyList() {
        CustomList<Student> list = new CustomArrayList<>();
        Comparator<Student> comparator = Comparator.naturalOrder();

        assertDoesNotThrow(() -> evenOddMergeSort.sort(list, comparator));
        assertEquals(0, list.size());
    }

    @Test
    void sort_ShouldHandleSingleElement() {
        CustomList<Student> list = new CustomArrayList<>();
        list.add(new Student.Builder().groupNumber("CS-101").averageScore(4.0).build());
        Comparator<Student> comparator = Comparator.naturalOrder();

        evenOddMergeSort.sort(list, comparator);

        assertEquals(1, list.size());
        assertEquals("CS-101", list.get(0).getGroupNumber());
    }

    @Test
    void sort_ShouldSortOnlyStudentsWithEvenAverageScore() {
        CustomList<Student> students = new CustomArrayList<>();
        students.add(new Student.Builder().groupNumber("CS-103").averageScore(4.5).build());
        students.add(new Student.Builder().groupNumber("CS-101").averageScore(3.5).build());
        students.add(new Student.Builder().groupNumber("CS-102").averageScore(2.8).build());
        students.add(new Student.Builder().groupNumber("CS-104").averageScore(5.0).build());

        evenOddMergeSort.sort(students, Comparator.comparing(Student::getGroupNumber));

        assertEquals("CS-102", students.get(0).getGroupNumber());
        assertEquals(2.8, students.get(0).getAverageScore());
        assertEquals("CS-101", students.get(1).getGroupNumber());
        assertEquals(3.5, students.get(1).getAverageScore());
        assertEquals("CS-103", students.get(2).getGroupNumber());
        assertEquals(4.5, students.get(2).getAverageScore());
        assertEquals("CS-104", students.get(3).getGroupNumber());
        assertEquals(5.0, students.get(3).getAverageScore());
    }

    @Test
    void sort_ShouldNotChangeListWhenAllScoresAreOdd() {
        CustomList<Student> students = new CustomArrayList<>();
        students.add(new Student.Builder().groupNumber("CS-105").averageScore(5.5).build());
        students.add(new Student.Builder().groupNumber("CS-103").averageScore(3.2).build());
        students.add(new Student.Builder().groupNumber("CS-101").averageScore(1.9).build());

        evenOddMergeSort.sort(students, Comparator.comparing(Student::getGroupNumber));

        assertEquals("CS-105", students.get(0).getGroupNumber());
        assertEquals("CS-103", students.get(1).getGroupNumber());
        assertEquals("CS-101", students.get(2).getGroupNumber());
    }

    @Test
    void sort_ShouldSortAllWhenAllScoresAreEven() {
        CustomList<Student> students = new CustomArrayList<>();
        students.add(new Student.Builder().groupNumber("CS-104").averageScore(4.9).build());
        students.add(new Student.Builder().groupNumber("CS-102").averageScore(2.1).build());
        students.add(new Student.Builder().groupNumber("CS-100").averageScore(0.5).build());

        evenOddMergeSort.sort(students, Comparator.comparing(Student::getGroupNumber));

        assertEquals("CS-100", students.get(0).getGroupNumber());
        assertEquals("CS-102", students.get(1).getGroupNumber());
        assertEquals("CS-104", students.get(2).getGroupNumber());
    }

    @Test
    void sort_ShouldSortByAverageScore_OnlyEvenStudents() {
        CustomList<Student> students = new CustomArrayList<>();
        students.add(new Student.Builder().averageScore(4.9).recordBookNumber("004").build());
        students.add(new Student.Builder().averageScore(3.5).recordBookNumber("003").build());
        students.add(new Student.Builder().averageScore(2.1).recordBookNumber("002").build());
        students.add(new Student.Builder().averageScore(5.0).recordBookNumber("005").build());

        evenOddMergeSort.sort(students, Comparator.comparingDouble(Student::getAverageScore));

        assertEquals(2.1, students.get(0).getAverageScore());
        assertEquals(3.5, students.get(1).getAverageScore());
        assertEquals(4.9, students.get(2).getAverageScore());
        assertEquals(5.0, students.get(3).getAverageScore());
    }

    @Test
    void sort_ShouldSortDescending_OnlyEvenStudents() {
        CustomList<Student> students = new CustomArrayList<>();
        students.add(new Student.Builder().averageScore(2.5).recordBookNumber("002").build());
        students.add(new Student.Builder().averageScore(3.5).recordBookNumber("003").build());
        students.add(new Student.Builder().averageScore(4.5).recordBookNumber("004").build());

        evenOddMergeSort.sort(students, Comparator.comparingDouble(Student::getAverageScore).reversed());

        assertEquals(4.5, students.get(0).getAverageScore());
        assertEquals(3.5, students.get(1).getAverageScore());
        assertEquals(2.5, students.get(2).getAverageScore());
    }

    @Test
    void sort_ShouldHandleComplexScenario() {
        CustomList<Student> students = new CustomArrayList<>();
        students.add(new Student.Builder()
                .groupNumber("CS-106")
                .averageScore(4.7)
                .recordBookNumber("2023-00006")
                .build());
        students.add(new Student.Builder()
                .groupNumber("CS-103")
                .averageScore(3.2)
                .recordBookNumber("2023-00003")
                .build());
        students.add(new Student.Builder()
                .groupNumber("CS-102")
                .averageScore(2.9)
                .recordBookNumber("2023-00002")
                .build());
        students.add(new Student.Builder()
                .groupNumber("CS-105")
                .averageScore(5.1)
                .recordBookNumber("2023-00005")
                .build());
        students.add(new Student.Builder()
                .groupNumber("CS-100")
                .averageScore(0.8)
                .recordBookNumber("2023-00000")
                .build());

        evenOddMergeSort.sort(students, Comparator.naturalOrder());

        assertEquals("CS-100", students.get(0).getGroupNumber());
        assertEquals(0.8, students.get(0).getAverageScore());

        assertEquals("CS-103", students.get(1).getGroupNumber());
        assertEquals(3.2, students.get(1).getAverageScore());

        assertEquals("CS-102", students.get(2).getGroupNumber());
        assertEquals(2.9, students.get(2).getAverageScore());

        assertEquals("CS-105", students.get(3).getGroupNumber());
        assertEquals(5.1, students.get(3).getAverageScore());

        assertEquals("CS-106", students.get(4).getGroupNumber());
        assertEquals(4.7, students.get(4).getAverageScore());
    }

    @Test
    void sort_ShouldHandleEdgeCaseWithScoreBoundaries() {
        CustomList<Student> students = new CustomArrayList<>();
        students.add(new Student.Builder().groupNumber("CS-102").averageScore(2.0).build());
        students.add(new Student.Builder().groupNumber("CS-103").averageScore(3.0).build());
        students.add(new Student.Builder().groupNumber("CS-100").averageScore(0.0).build());
        students.add(new Student.Builder().groupNumber("CS-104").averageScore(4.0).build());

        evenOddMergeSort.sort(students, Comparator.comparing(Student::getGroupNumber));

        assertEquals("CS-100", students.get(0).getGroupNumber());
        assertEquals("CS-103", students.get(1).getGroupNumber());
        assertEquals("CS-102", students.get(2).getGroupNumber());
        assertEquals("CS-104", students.get(3).getGroupNumber());
    }

    @Test
    void sort_ShouldHandleLargeList() {
        CustomList<Student> students = new CustomArrayList<>();

        for (int i = 100; i > 0; i--) {
            double score = i % 2 == 0 ? 4.5 : 3.5;
            students.add(new Student.Builder()
                    .groupNumber("CS-" + String.format("%03d", i))
                    .averageScore(score)
                    .build());
        }

        evenOddMergeSort.sort(students, Comparator.comparing(Student::getGroupNumber));

        for (int i = 0; i < students.size() - 1; i++) {
            Student current = students.get(i);
            Student next = students.get(i + 1);

            if (((int) current.getAverageScore()) % 2 == 0 && ((int) next.getAverageScore()) % 2 == 0) {
                assertTrue(current.getGroupNumber().compareTo(next.getGroupNumber()) <= 0);
            }
        }
    }

    @Test
    void sort_ShouldPreserveRelativeOrderOfOddStudents() {
        CustomList<Student> students = new CustomArrayList<>();
        Student odd1 = new Student.Builder().groupNumber("ODD-03").averageScore(3.5).build();
        Student even1 = new Student.Builder().groupNumber("EVEN-04").averageScore(4.5).build();
        Student odd2 = new Student.Builder().groupNumber("ODD-01").averageScore(1.5).build();
        Student even2 = new Student.Builder().groupNumber("EVEN-02").averageScore(2.5).build();
        Student odd3 = new Student.Builder().groupNumber("ODD-05").averageScore(5.5).build();

        students.add(odd1);
        students.add(even1);
        students.add(odd2);
        students.add(even2);
        students.add(odd3);

        evenOddMergeSort.sort(students, Comparator.comparing(Student::getGroupNumber));

        assertEquals("ODD-03", students.get(0).getGroupNumber());
        assertEquals("EVEN-02", students.get(1).getGroupNumber());
        assertEquals("ODD-01", students.get(2).getGroupNumber());
        assertEquals("EVEN-04", students.get(3).getGroupNumber());
        assertEquals("ODD-05", students.get(4).getGroupNumber());
    }

    @Test
    void sort_ShouldHandleTwoEvenStudents() {
        CustomList<Student> students = new CustomArrayList<>();
        students.add(new Student.Builder().groupNumber("CS-102").averageScore(4.5).build());
        students.add(new Student.Builder().groupNumber("CS-101").averageScore(2.5).build());

        evenOddMergeSort.sort(students, Comparator.comparing(Student::getGroupNumber));

        assertEquals("CS-101", students.get(0).getGroupNumber());
        assertEquals("CS-102", students.get(1).getGroupNumber());
    }
}
