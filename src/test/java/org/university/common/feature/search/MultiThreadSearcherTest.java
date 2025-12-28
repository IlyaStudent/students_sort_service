package org.university.common.feature.search;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.university.common.collection.CustomArrayList;
import org.university.common.collection.CustomList;
import org.university.common.model.Student;
import org.university.feature.search.MultiThreadSearcher;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class MultiThreadSearcherTest {

    private static MultiThreadSearcher searcher;
    private static Random random;

    @BeforeAll
    static void setUp() {
        searcher = new MultiThreadSearcher();
        random = new Random();
    }

    @Test
    @DisplayName("Пустая коллекция возвращает 0")
    void countOccurrences_whenCollectionIsEmpty_returnsZero() {
        CustomList<Student> emptyList = new CustomArrayList<>();
        int result = searcher.countOccurrences(emptyList, "TT-001");
        assertEquals(0, result);
    }

    @Test
    @DisplayName("null коллекция возвращает 0")
    void countOccurrences_whenCollectionIsNull_returnsZero() {
        int result = searcher.countOccurrences(null, "TT-001");
        assertEquals(0, result);
    }

    @ParameterizedTest
    @CsvSource({
            "20, 2, TY",
            "1000, 420, SA-101",
            "9999, 123, SS-110",
            "100, 50, GROUP-HALF"})
    @DisplayName("Правильный подсчет студентов в группе")
    void countOccurrences_whenAllStudentsInGroup_returnsAll(
            int sizeCollection, int expected, String group) {

        CustomList<Student> students = createStudentList(sizeCollection, expected, group);
        int result = searcher.countOccurrences(students, group);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Все студенты в искомой группе")
    void countOccurrences_whenAllStudentsInGroup_returnsAll() {
        CustomList<Student> students = new CustomArrayList<>();
        for (int i = 0; i < 5; i++) {
            students.add(
                    createStudent(
                            "GROUP-ALL",
                            random.nextDouble(5.5),
                            "2025-" + random.nextInt(50_000)
                    )
            );
        }

        int result = searcher.countOccurrences(students, "GROUP-ALL");
        assertEquals(5, result);
    }

    @Test
    @DisplayName("Ни одного студента в искомой группе")
    void countOccurrences_whenNoStudentsInGroup_returnsZero() {
        CustomList<Student> students = new CustomArrayList<>();
        for (int i = 0; i < 10_000; i++) {
            students.add(
                    createStudent(
                            "GROUP-A",
                            random.nextDouble(5.5),
                            "2025-" + random.nextInt(50_000)
                    )
            );
        }

        int result = searcher.countOccurrences(students, "GROUP-B");
        assertEquals(0, result);
    }

    @Test
    @DisplayName("Один студент в большой коллекции")
    void countOccurrences_whenOneStudentInLargeCollection_returnsOne() {
        CustomList<Student> students = new CustomArrayList<>();

        for (int i = 0; i < 99; i++) {
            students.add(
                    createStudent(
                            "GROUP-" + i,
                            random.nextDouble(5.5),
                            "2025-" + random.nextInt(50_000)
                    )
            );
        }

        students.add(
                createStudent(
                        "GROUP-SOCHNO",
                        random.nextDouble(5.5),
                        "2025-" + random.nextInt(50_000)
                )
        );

        int result = searcher.countOccurrences(students, "GROUP-SOCHNO");
        assertEquals(1, result);
    }

    private CustomList<Student> createStudentList(
            int total, int inTargetGroup, String targetGroup) {

        CustomList<Student> students = new CustomArrayList<>() {};

        for (int i = 0; i < inTargetGroup; i++) {
            students.add(
                    createStudent(
                            targetGroup,
                            random.nextDouble(5.5),
                            "2025-" + random.nextInt(50_000)
                    )
            );
        }

        for (int i = inTargetGroup; i < total; i++) {
            students.add(
                    createStudent(
                            "GROUP-" + i,
                            random.nextDouble(5.5),
                            "2025-" + random.nextInt(50_000)
                    )
            );
        }

        return students;
    }

    private Student createStudent(String groupNumber, double averageScore, String recordBookNumber) {
        return new Student.Builder()
                .groupNumber(groupNumber)
                .averageScore(averageScore)
                .recordBookNumber(recordBookNumber)
                .build();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 5, 10, 100})
    @DisplayName("Разные размеры коллекции обрабатываются корректно")
    void countOccurrences_withDifferentCollectionSizes_worksCorrectly(int size) {
        CustomList<Student> students = new CustomArrayList<>();
        for (int i = 0; i < size; i++) {
            students.add(
                    createStudent(
                            "GROUP-" + (i % 3),
                            random.nextDouble(5.5),
                            "2025-" + random.nextInt(50_000))
            );
        }

        int result = searcher.countOccurrences(students, "GROUP-1");
        long expected = students.stream()
                                .filter(s -> "GROUP-1".equals(s.getGroupNumber()))
                                .count();

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Тестирование приватного метода createTasks через рефлексию")
    void createTasks_createsCorrectNumberOfTasks() throws Exception {

        CustomList<Student> students = new CustomArrayList<>();
        for (int i = 0; i < 10; i++) {
            students.add(
                    createStudent(
                            "GROUP-" + i,
                            random.nextDouble(5.5),
                            "2025-" + random.nextInt(50_000))
            );
        }

        Method method =
                MultiThreadSearcher.class.getDeclaredMethod(
                        "createTasks", CustomList.class, String.class, int.class);
        method.setAccessible(true);

        CustomList<?> tasks = (CustomList<?>) method.invoke(searcher, students, "GROUP-5", 3);
        Method sizeMethod = tasks.getClass().getMethod("size");

        int taskCount = (int) sizeMethod.invoke(tasks);
        assertEquals(3, taskCount);
    }

    @Test
    @DisplayName("Тестирование внутреннего класса Searcher")
    void searcherRecord_countsCorrectly() throws Exception {
        CustomList<Student> students = new CustomArrayList<>();

        students.add(createStudent("GROUP-A", random.nextDouble(5.5), "TEST-A"));

        students.add(createStudent("GROUP-B", random.nextDouble(5.5), "TEST-B"));

        students.add(createStudent("GROUP-A", random.nextDouble(5.5), "TEST-A"));

        students.add(createStudent("GROUP-C", random.nextDouble(5.5), "TEST-C"));

        students.add(createStudent("GROUP-A", random.nextDouble(5.5), "TEST-A"));

        Class<?> searcherClass =
                Class.forName("org.university.feature.search.MultiThreadSearcher$Searcher");

        Constructor<?> constructor = searcherClass.getDeclaredConstructors()[0];
        constructor.setAccessible(true);

        Object searcherInstance = constructor.newInstance(students, 0, 5, "GROUP-A");

        Method callMethod = searcherClass.getMethod("call");
        callMethod.setAccessible(true);

        int result = (int) callMethod.invoke(searcherInstance);
        assertEquals(3, result);
    }

    @Test
    @DisplayName("Проверка граничных случаев с одним студентом")
    void countOccurrences_withSingleStudent() {
        CustomList<Student> students = new CustomArrayList<>();
        students.add(createStudent("GROUP-A", random.nextDouble(5.5), "TEST"));

        int result1 = searcher.countOccurrences(students, "GROUP-A");
        int result2 = searcher.countOccurrences(students, "GROUP-B");

        assertEquals(1, result1);
        assertEquals(0, result2);
    }

    @Test
    @DisplayName("Проверка с большим количеством студентов в одной группе")
    void countOccurrences_withManyStudentsInSameGroup() {
        CustomList<Student> students = new CustomArrayList<>();
        int targetCount = 1000;

        for (int i = 0; i < targetCount; i++) {
            students.add(
                    createStudent(
                            "MASSIVE-GROUP",
                            random.nextDouble(5.5),
                            "TEST"
                    )
            );
        }

        int result = searcher.countOccurrences(students, "MASSIVE-GROUP");
        assertEquals(targetCount, result);
    }
}
