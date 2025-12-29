package org.university.feature.sorting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.university.common.collection.CustomArrayList;
import org.university.common.collection.CustomList;
import org.university.common.model.Student;
import org.university.feature.sorting.strategy.SortStrategy;
import org.university.feature.sorting.strategy.impl.usual_sorts.BubbleSortStrategy;
import org.university.feature.sorting.strategy.impl.usual_sorts.QuickSortStrategy;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class SortContextTest {

    private SortContext sortContext;

    @BeforeEach
    void setUp() {
        sortContext = new SortContext();
    }

    @Test
    void defaultConstructor_ShouldCreateContextWithNullStrategy() {
        SortContext context = new SortContext();
        assertNull(context.getStrategy());
    }

    @Test
    void constructorWithStrategy_ShouldSetStrategy() {
        SortStrategy strategy = new BubbleSortStrategy();
        SortContext context = new SortContext(strategy);

        assertNotNull(context.getStrategy());
        assertEquals(strategy, context.getStrategy());
    }

    @Test
    void constructorWithStrategy_ShouldThrowException_WhenStrategyIsNull() {
        assertThrows(NullPointerException.class, () -> new SortContext(null));
    }

    @Test
    void setStrategy_ShouldSetTheStrategy() {
        SortStrategy strategy = new BubbleSortStrategy();
        sortContext.setStrategy(strategy);

        assertEquals(strategy, sortContext.getStrategy());
    }

    @Test
    void setStrategy_ShouldThrowException_WhenStrategyIsNull() {
        assertThrows(NullPointerException.class, () -> sortContext.setStrategy(null));
    }

    @Test
    void getStrategy_ShouldReturnNull_WhenNotSet() {
        assertNull(sortContext.getStrategy());
    }

    @Test
    void getCurrentStrategyName_ShouldReturnNone_WhenStrategyIsNull() {
        assertEquals("None", sortContext.getCurrentStrategyName());
    }

    @Test
    void getCurrentStrategyName_ShouldReturnStrategyName_WhenStrategyIsSet() {
        sortContext.setStrategy(new BubbleSortStrategy());
        assertEquals("Пузырьковая сортировка", sortContext.getCurrentStrategyName());
    }

    @Test
    void executeSort_ShouldThrowException_WhenStrategyIsNotSet() {
        CustomList<Integer> list = new CustomArrayList<>();
        list.add(3);
        list.add(1);
        list.add(2);

        assertThrows(IllegalStateException.class, () ->
                sortContext.executeSort(list, Comparator.naturalOrder())
        );
    }

    @Test
    void executeSortWithoutComparator_ShouldThrowException_WhenStrategyIsNotSet() {
        CustomList<Integer> list = new CustomArrayList<>();
        list.add(3);
        list.add(1);
        list.add(2);

        assertThrows(IllegalStateException.class, () ->
                sortContext.executeSort(list)
        );
    }

    @Test
    void executeSort_ShouldSortList_WhenStrategyIsSet() {
        CustomList<Integer> list = new CustomArrayList<>();
        list.add(5);
        list.add(2);
        list.add(8);
        list.add(1);

        sortContext.setStrategy(new BubbleSortStrategy());
        sortContext.executeSort(list, Comparator.naturalOrder());

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(5, list.get(2));
        assertEquals(8, list.get(3));
    }

    @Test
    void executeSortWithoutComparator_ShouldSortList_WhenStrategyIsSet() {
        CustomList<Integer> list = new CustomArrayList<>();
        list.add(5);
        list.add(2);
        list.add(8);
        list.add(1);

        sortContext.setStrategy(new BubbleSortStrategy());
        sortContext.executeSort(list);

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(5, list.get(2));
        assertEquals(8, list.get(3));
    }

    @Test
    void executeSort_ShouldSortStudents_WhenStrategyIsSet() {
        CustomList<Student> students = new CustomArrayList<>();
        students.add(new Student.Builder().groupNumber("CS-103").build());
        students.add(new Student.Builder().groupNumber("CS-101").build());
        students.add(new Student.Builder().groupNumber("CS-102").build());

        sortContext.setStrategy(new QuickSortStrategy());
        sortContext.executeSort(students, Comparator.comparing(Student::getGroupNumber));

        assertEquals("CS-101", students.get(0).getGroupNumber());
        assertEquals("CS-102", students.get(1).getGroupNumber());
        assertEquals("CS-103", students.get(2).getGroupNumber());
    }

    @Test
    void executeSortWithoutComparator_ShouldSortStudentsNaturally_WhenStrategyIsSet() {
        CustomList<Student> students = new CustomArrayList<>();
        students.add(new Student.Builder()
                .groupNumber("CS-102")
                .averageScore(4.0)
                .build());
        students.add(new Student.Builder()
                .groupNumber("CS-101")
                .averageScore(5.0)
                .build());
        students.add(new Student.Builder()
                .groupNumber("CS-101")
                .averageScore(3.0)
                .build());

        sortContext.setStrategy(new QuickSortStrategy());
        sortContext.executeSort(students);

        assertEquals("CS-101", students.get(0).getGroupNumber());
        assertEquals(3.0, students.get(0).getAverageScore());
        assertEquals("CS-101", students.get(1).getGroupNumber());
        assertEquals(5.0, students.get(1).getAverageScore());
        assertEquals("CS-102", students.get(2).getGroupNumber());
    }

    @Test
    void setStrategy_ShouldAllowChangingStrategy() {
        sortContext.setStrategy(new BubbleSortStrategy());
        assertEquals("Пузырьковая сортировка", sortContext.getCurrentStrategyName());

        sortContext.setStrategy(new QuickSortStrategy());
        assertEquals("Быстрая сортировка", sortContext.getCurrentStrategyName());
    }

    @Test
    void executeSort_ShouldUseMostRecentlySetStrategy() {
        CustomList<Integer> list = new CustomArrayList<>();
        list.add(3);
        list.add(1);
        list.add(2);

        sortContext.setStrategy(new BubbleSortStrategy());
        sortContext.setStrategy(new QuickSortStrategy());

        sortContext.executeSort(list, Comparator.naturalOrder());

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
        assertEquals("Быстрая сортировка", sortContext.getCurrentStrategyName());
    }

    @Test
    void executeSort_ShouldWorkWithEmptyList() {
        CustomList<Integer> list = new CustomArrayList<>();
        sortContext.setStrategy(new BubbleSortStrategy());

        assertDoesNotThrow(() -> sortContext.executeSort(list, Comparator.naturalOrder()));
        assertEquals(0, list.size());
    }

    @Test
    void executeSortWithoutComparator_ShouldWorkWithEmptyList() {
        CustomList<Integer> list = new CustomArrayList<>();
        sortContext.setStrategy(new BubbleSortStrategy());

        assertDoesNotThrow(() -> sortContext.executeSort(list));
        assertEquals(0, list.size());
    }

    @Test
    void executeSort_ShouldWorkWithSingleElement() {
        CustomList<Integer> list = new CustomArrayList<>();
        list.add(42);
        sortContext.setStrategy(new BubbleSortStrategy());

        sortContext.executeSort(list, Comparator.naturalOrder());

        assertEquals(1, list.size());
        assertEquals(42, list.get(0));
    }

    @Test
    void executeSort_ShouldSortDescending() {
        CustomList<Integer> list = new CustomArrayList<>();
        list.add(1);
        list.add(5);
        list.add(2);
        list.add(8);

        sortContext.setStrategy(new QuickSortStrategy());
        sortContext.executeSort(list, Comparator.reverseOrder());

        assertEquals(8, list.get(0));
        assertEquals(5, list.get(1));
        assertEquals(2, list.get(2));
        assertEquals(1, list.get(3));
    }

    @Test
    void contextWithStrategy_ShouldBeUsableImmediately() {
        SortStrategy strategy = new BubbleSortStrategy();
        SortContext context = new SortContext(strategy);

        CustomList<Integer> list = new CustomArrayList<>();
        list.add(3);
        list.add(1);
        list.add(2);

        context.executeSort(list, Comparator.naturalOrder());

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }
}
