package org.university.common.util;

import org.junit.jupiter.api.Test;
import org.university.common.collection.CustomList;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StreamHelperTest {

    @Test
    void constructor_ShouldThrowAssertionError() {
        try {
            Constructor<StreamHelper> constructor = StreamHelper.class.getDeclaredConstructor();
            constructor.setAccessible(true);

            InvocationTargetException exception = assertThrows(
                    InvocationTargetException.class,
                    constructor::newInstance
            );

            assertInstanceOf(AssertionError.class, exception.getCause());
            assertEquals("Cannot instantiate StreamHelper class", exception.getCause().getMessage());
        } catch (NoSuchMethodException e) {
            fail("Constructor should exist");
        }
    }

    @Test
    void toCustomList_ShouldReturnEmptyList_WhenStreamIsEmpty() {
        CustomList<String> result = Stream.<String>empty()
                .collect(StreamHelper.toCustomList());

        assertNotNull(result);
        assertEquals(0, result.size());
        assertTrue(result.isEmpty());
    }

    @Test
    void toCustomList_ShouldCollectStrings() {
        CustomList<String> result = Stream.of("A", "B", "C")
                .collect(StreamHelper.toCustomList());

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("A", result.get(0));
        assertEquals("B", result.get(1));
        assertEquals("C", result.get(2));
    }

    @Test
    void toCustomList_ShouldCollectIntegers() {
        CustomList<Integer> result = Stream.of(1, 2, 3, 4, 5)
                .collect(StreamHelper.toCustomList());

        assertNotNull(result);
        assertEquals(5, result.size());
        assertEquals(1, result.get(0));
        assertEquals(2, result.get(1));
        assertEquals(3, result.get(2));
        assertEquals(4, result.get(3));
        assertEquals(5, result.get(4));
    }

    @Test
    void toCustomList_ShouldWorkWithLargeStream() {
        int size = 10000;
        CustomList<Integer> result = Stream.iterate(0, i -> i + 1)
                .limit(size)
                .collect(StreamHelper.toCustomList());

        assertNotNull(result);
        assertEquals(size, result.size());
        assertEquals(0, result.get(0));
        assertEquals(9999, result.get(9999));
    }

    @Test
    void toCustomList_ShouldCollectNullElements() {
        CustomList<String> result = Stream.of("A", null, "C", null)
                .collect(StreamHelper.toCustomList());

        assertNotNull(result);
        assertEquals(4, result.size());
        assertEquals("A", result.get(0));
        assertNull(result.get(1));
        assertEquals("C", result.get(2));
        assertNull(result.get(3));
    }

    @Test
    void toCustomList_ShouldWorkWithFilteredStream() {
        CustomList<Integer> result = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .filter(n -> n % 2 == 0)
                .collect(StreamHelper.toCustomList());

        assertNotNull(result);
        assertEquals(5, result.size());
        assertEquals(2, result.get(0));
        assertEquals(4, result.get(1));
        assertEquals(6, result.get(2));
        assertEquals(8, result.get(3));
        assertEquals(10, result.get(4));
    }

    @Test
    void toCustomList_ShouldWorkWithMappedStream() {
        CustomList<String> result = Stream.of(1, 2, 3)
                .map(n -> "Number: " + n)
                .collect(StreamHelper.toCustomList());

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Number: 1", result.get(0));
        assertEquals("Number: 2", result.get(1));
        assertEquals("Number: 3", result.get(2));
    }

    @Test
    void toCustomList_ShouldWorkWithParallelStream() {
        List<Integer> sourceList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        CustomList<Integer> result = sourceList.parallelStream()
                .collect(StreamHelper.toCustomList());

        assertNotNull(result);
        assertEquals(10, result.size());

        for (int i = 1; i <= 10; i++) {
            assertTrue(result.contains(i), "List should contain " + i);
        }
    }

    @Test
    void toCustomList_ShouldCombineLists_InParallelStream() {
        int size = 1000;

        CustomList<Integer> result = Stream.iterate(0, i -> i + 1)
                .limit(size)
                .parallel()
                .collect(StreamHelper.toCustomList());

        assertNotNull(result);
        assertEquals(size, result.size());

        for (int i = 0; i < size; i++) {
            assertTrue(result.contains(i), "List should contain " + i);
        }
    }

    @Test
    void toCustomList_ShouldWorkWithComplexObjects() {
        class Person {
            final String name;
            final int age;

            Person(String name, int age) {
                this.name = name;
                this.age = age;
            }
        }

        CustomList<Person> result = Stream.of(
                new Person("Alice", 25),
                new Person("Bob", 30),
                new Person("Charlie", 35)
        ).collect(StreamHelper.toCustomList());

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Alice", result.get(0).name);
        assertEquals(25, result.get(0).age);
        assertEquals("Bob", result.get(1).name);
        assertEquals(30, result.get(1).age);
        assertEquals("Charlie", result.get(2).name);
        assertEquals(35, result.get(2).age);
    }

    @Test
    void toCustomList_ShouldMaintainOrder_InSequentialStream() {
        CustomList<Integer> result = Stream.of(5, 3, 8, 1, 9, 2)
                .collect(StreamHelper.toCustomList());

        assertNotNull(result);
        assertEquals(6, result.size());
        assertEquals(5, result.get(0));
        assertEquals(3, result.get(1));
        assertEquals(8, result.get(2));
        assertEquals(1, result.get(3));
        assertEquals(9, result.get(4));
        assertEquals(2, result.get(5));
    }

    @Test
    void toCustomList_ShouldWorkWithStreamBuilder() {
        Stream.Builder<String> builder = Stream.builder();
        builder.add("First");
        builder.add("Second");
        builder.add("Third");

        CustomList<String> result = builder.build()
                .collect(StreamHelper.toCustomList());

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("First", result.get(0));
        assertEquals("Second", result.get(1));
        assertEquals("Third", result.get(2));
    }

    @Test
    void toCustomList_ShouldWorkWithFlatMap() {
        CustomList<String> result = Stream.of(
                Arrays.asList("A", "B"),
                Arrays.asList("C", "D"),
                Arrays.asList("E", "F")
        )
                .flatMap(List::stream)
                .collect(StreamHelper.toCustomList());

        assertNotNull(result);
        assertEquals(6, result.size());
        assertEquals("A", result.get(0));
        assertEquals("B", result.get(1));
        assertEquals("C", result.get(2));
        assertEquals("D", result.get(3));
        assertEquals("E", result.get(4));
        assertEquals("F", result.get(5));
    }

    @Test
    void toCustomList_ShouldWorkWithDistinct() {
        CustomList<Integer> result = Stream.of(1, 2, 2, 3, 3, 3, 4, 4, 4, 4)
                .distinct()
                .collect(StreamHelper.toCustomList());

        assertNotNull(result);
        assertEquals(4, result.size());
        assertEquals(1, result.get(0));
        assertEquals(2, result.get(1));
        assertEquals(3, result.get(2));
        assertEquals(4, result.get(3));
    }

    @Test
    void toCustomList_ShouldWorkWithSorted() {
        CustomList<Integer> result = Stream.of(5, 2, 8, 1, 9, 3)
                .sorted()
                .collect(StreamHelper.toCustomList());

        assertNotNull(result);
        assertEquals(6, result.size());
        assertEquals(1, result.get(0));
        assertEquals(2, result.get(1));
        assertEquals(3, result.get(2));
        assertEquals(5, result.get(3));
        assertEquals(8, result.get(4));
        assertEquals(9, result.get(5));
    }

    @Test
    void toCustomList_CollectorCharacteristics_ShouldHaveIdentityFinish() {
        var collector = StreamHelper.toCustomList();

        assertTrue(
                collector.characteristics().contains(
                        java.util.stream.Collector.Characteristics.IDENTITY_FINISH
                ),
                "Collector should have IDENTITY_FINISH characteristic"
        );
    }

    @Test
    void toCustomList_ShouldBeReusable() {
        var collector = StreamHelper.<String>toCustomList();

        CustomList<String> result1 = Stream.of("A", "B", "C")
                .collect(collector);

        CustomList<String> result2 = Stream.of("X", "Y", "Z")
                .collect(collector);

        assertEquals(3, result1.size());
        assertEquals(3, result2.size());
        assertEquals("A", result1.get(0));
        assertEquals("X", result2.get(0));
    }
}
