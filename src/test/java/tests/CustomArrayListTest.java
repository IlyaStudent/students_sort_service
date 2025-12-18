package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.university.common.collection.CustomArrayList;
import org.university.common.collection.CustomList;

import java.util.List;
import java.util.stream.Collectors;

class CustomArrayListTest {

    // TODO: write more tests

    @Test
    void addAndGetShouldWork() {
        CustomList<String> list = new CustomArrayList<>();
        list.add("A");
        list.add("B");

        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals("A", list.get(0));
        Assertions.assertEquals("B", list.get(1));
    }

    @Test
    void setShouldReplaceElement() {
        CustomList<Integer> list = new CustomArrayList<>();
        list.add(1);
        list.add(2);

        list.set(1, 999);

        Assertions.assertEquals(999, list.get(1));
        Assertions.assertEquals(2, list.size());
    }

    @Test
    void removeShouldShiftElements() {
        CustomList<String> list = new CustomArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        String removed = list.remove(1);

        Assertions.assertEquals("B", removed);
        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals("A", list.get(0));
        Assertions.assertEquals("C", list.get(1));
    }

    @Test
    void isEmptyAndClearShouldWork() {
        CustomList<String> list = new CustomArrayList<>();
        Assertions.assertTrue(list.isEmpty());

        list.add("X");
        Assertions.assertFalse(list.isEmpty());

        list.clear();
        Assertions.assertTrue(list.isEmpty());
        Assertions.assertEquals(0, list.size());
    }

    @Test
    void streamShouldReturnAllElementsInOrder() {
        CustomList<Integer> list = new CustomArrayList<>();
        list.add(3);
        list.add(1);
        list.add(2);

        List<Integer> result = list.stream().collect(Collectors.toList());

        Assertions.assertEquals(List.of(3, 1, 2), result);
    }

    @Test
    void toArrayShouldReturnArrayWithSameElements() {
        CustomList<String> list = new CustomArrayList<>();
        list.add("A");
        list.add("B");

        Object[] arr = list.toArray();

        Assertions.assertEquals(2, arr.length);
        Assertions.assertEquals("A", arr[0]);
        Assertions.assertEquals("B", arr[1]);
    }

    @Test
    void shouldGrowCapacityOnManyAdds() {
        CustomList<Integer> list = new CustomArrayList<>(1);
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }

        Assertions.assertEquals(100, list.size());
        Assertions.assertEquals(0, list.get(0));
        Assertions.assertEquals(99, list.get(99));
    }

    @Test
    void getShouldThrowOnBadIndex() {
        CustomList<String> list = new CustomArrayList<>();
        list.add("A");

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
    }

    @Test
    void removeShouldThrowOnBadIndex() {
        CustomList<String> list = new CustomArrayList<>();
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));
    }
}
