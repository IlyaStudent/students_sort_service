package org.university.common.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

class CustomArrayListTest {

    @Test
    void addAndGetShouldWork() {
        CustomList<String> list = new CustomArrayList<>();
        list.add(-1, "A");
        list.add(-1, "B");

        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals("A", list.get(0));
        Assertions.assertEquals("B", list.get(1));
    }

    @Test
    void setShouldReplaceElement() {
        CustomList<Integer> list = new CustomArrayList<>();
        list.add(-1, 1);
        list.add(-1, 2);

        list.set(1, 999);

        Assertions.assertEquals(999, list.get(1));
        Assertions.assertEquals(2, list.size());
    }

    @Test
    void removeShouldShiftElements() {
        CustomList<String> list = new CustomArrayList<>();
        list.add(-1, "A");
        list.add(-1, "B");
        list.add(-1, "C");

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

        list.add(-1, "X");
        Assertions.assertFalse(list.isEmpty());

        list.clear();
        Assertions.assertTrue(list.isEmpty());
        Assertions.assertEquals(0, list.size());
    }

    @Test
    void streamShouldReturnAllElementsInOrder() {
        CustomList<Integer> list = new CustomArrayList<>();
        list.add(-1, 3);
        list.add(-1, 1);
        list.add(-1, 2);

        List<Integer> result = list.stream().collect(Collectors.toList());

        Assertions.assertEquals(List.of(3, 1, 2), result);
    }

    @Test
    void toArrayShouldReturnArrayWithSameElements() {
        CustomList<String> list = new CustomArrayList<>();
        list.add(-1, "A");
        list.add(-1, "B");

        Object[] arr = list.toArray(new String[0]);

        Assertions.assertEquals(2, arr.length);
        Assertions.assertEquals("A", arr[0]);
        Assertions.assertEquals("B", arr[1]);
    }

    @Test
    void shouldGrowCapacityOnManyAdds() {
        CustomList<Integer> list = new CustomArrayList<>(1);
        for (int i = 0; i < 100; i++) {
            list.add(-1, i);
        }

        Assertions.assertEquals(100, list.size());
        Assertions.assertEquals(0, list.get(0));
        Assertions.assertEquals(99, list.get(99));
    }

    @Test
    void getShouldThrowOnBadIndex() {
        CustomList<String> list = new CustomArrayList<>();
        list.add(-1, "A");

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
    }

    @Test
    void removeShouldThrowOnBadIndex() {
        CustomList<String> list = new CustomArrayList<>();
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));
    }

    @Test
    void addAtIndexShouldInsertElement() {
        CustomList<String> list = new CustomArrayList<>();
        list.add(-1, "A");
        list.add(-1, "C");
        list.add(1, "B");

        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals("A", list.get(0));
        Assertions.assertEquals("B", list.get(1));
        Assertions.assertEquals("C", list.get(2));
    }

    @Test
    void addAtIndexAtBeginningShouldWork() {
        CustomList<String> list = new CustomArrayList<>();
        list.add(-1, "B");
        list.add(0, "A");

        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals("A", list.get(0));
        Assertions.assertEquals("B", list.get(1));
    }

    @Test
    void addAtIndexAtEndShouldWork() {
        CustomList<String> list = new CustomArrayList<>();
        list.add(-1, "A");
        list.add(1, "B");

        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals("A", list.get(0));
        Assertions.assertEquals("B", list.get(1));
    }

    @Test
    void addAtIndexShouldThrowOnBadIndex() {
        CustomList<String> list = new CustomArrayList<>();
        list.add(-1, "A");

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.add(2, "B"));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, "B"));
    }

    @Test
    void setShouldThrowOnBadIndex() {
        CustomList<String> list = new CustomArrayList<>();
        list.add(-1, "A");

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.set(1, "B"));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "B"));
    }

    @Test
    void removeLastElementShouldWork() {
        CustomList<String> list = new CustomArrayList<>();
        list.add(-1, "A");
        list.add(-1, "B");
        list.add(-1, "C");

        String removed = list.remove(2);

        Assertions.assertEquals("C", removed);
        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals("A", list.get(0));
        Assertions.assertEquals("B", list.get(1));
    }

    @Test
    void removeFirstElementShouldWork() {
        CustomList<String> list = new CustomArrayList<>();
        list.add(-1, "A");
        list.add(-1, "B");
        list.add(-1, "C");

        String removed = list.remove(0);

        Assertions.assertEquals("A", removed);
        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals("B", list.get(0));
        Assertions.assertEquals("C", list.get(1));
    }

    @Test
    void containsShouldReturnCorrectResult() {
        CustomList<String> list = new CustomArrayList<>();
        list.add(-1, "A");
        list.add(-1, "B");
        list.add(-1, "C");

        Assertions.assertTrue(list.contains("A"));
        Assertions.assertTrue(list.contains("B"));
        Assertions.assertTrue(list.contains("C"));
        Assertions.assertFalse(list.contains("D"));
        Assertions.assertFalse(list.contains(null));
    }

    @Test
    void containsWithNullShouldWork() {
        CustomList<String> list = new CustomArrayList<>();
        list.add(-1, "A");
        list.add(-1, null);
        list.add(-1, "C");

        Assertions.assertTrue(list.contains(null));
        Assertions.assertTrue(list.contains("A"));
        Assertions.assertFalse(list.contains("B"));
    }

    @Test
    void indexOfShouldReturnCorrectIndex() {
        CustomList<String> list = new CustomArrayList<>();
        list.add(-1, "A");
        list.add(-1, "B");
        list.add(-1, "A");
        list.add(-1, "C");

        Assertions.assertEquals(0, list.indexOf("A"));
        Assertions.assertEquals(1, list.indexOf("B"));
        Assertions.assertEquals(3, list.indexOf("C"));
        Assertions.assertEquals(-1, list.indexOf("D"));
        Assertions.assertEquals(-1, list.indexOf(null));
    }

    @Test
    void indexOfWithNullShouldWork() {
        CustomList<String> list = new CustomArrayList<>();
        list.add(-1, "A");
        list.add(-1, null);
        list.add(-1, "B");
        list.add(-1, null);

        Assertions.assertEquals(1, list.indexOf(null));
        Assertions.assertEquals(0, list.indexOf("A"));
    }

    @Test
    void lastIndexOfShouldReturnCorrectIndex() {
        CustomList<String> list = new CustomArrayList<>();
        list.add(-1, "A");
        list.add(-1, "B");
        list.add(-1, "A");
        list.add(-1, "C");

        Assertions.assertEquals(2, list.lastIndexOf("A"));
        Assertions.assertEquals(1, list.lastIndexOf("B"));
        Assertions.assertEquals(3, list.lastIndexOf("C"));
        Assertions.assertEquals(-1, list.lastIndexOf("D"));
        Assertions.assertEquals(-1, list.lastIndexOf(null));
    }

    @Test
    void lastIndexOfWithNullShouldWork() {
        CustomList<String> list = new CustomArrayList<>();
        list.add(-1, "A");
        list.add(-1, null);
        list.add(-1, "B");
        list.add(-1, null);

        Assertions.assertEquals(3, list.lastIndexOf(null));
        Assertions.assertEquals(2, list.lastIndexOf("B"));
    }

    @Test
    void toArrayWithTypeShouldWork() {
        CustomList<String> list = new CustomArrayList<>();
        list.add(-1, "A");
        list.add(-1, "B");
        list.add(-1, "C");

        String[] array = list.toArray(new String[0]);
        Assertions.assertEquals(3, array.length);
        Assertions.assertArrayEquals(new String[]{"A", "B", "C"}, array);
    }

    @Test
    void toArrayWithLargerArrayShouldWork() {
        CustomList<String> list = new CustomArrayList<>();
        list.add(-1, "A");
        list.add(-1, "B");

        String[] array = new String[5];
        array[2] = "Existing";
        String[] result = list.toArray(array);

        Assertions.assertSame(array, result);
        Assertions.assertEquals("A", array[0]);
        Assertions.assertEquals("B", array[1]);
        Assertions.assertNull(array[2]);
        Assertions.assertEquals("Existing", array[2]);
    }

    @Test
    void iteratorShouldTraverseAllElements() {
        CustomList<String> list = new CustomArrayList<>();
        list.add(-1, "A");
        list.add(-1, "B");
        list.add(-1, "C");

        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s);
        }

        Assertions.assertEquals("ABC", sb.toString());
    }

    @Test
    void iteratorHasNextOnEmptyListShouldReturnFalse() {
        CustomList<String> list = new CustomArrayList<>();
        var iterator = list.iterator();
        Assertions.assertFalse(iterator.hasNext());
    }

    @Test
    void iteratorNextOnEmptyListShouldThrow() {
        CustomList<String> list = new CustomArrayList<>();
        var iterator = list.iterator();
        Assertions.assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void iteratorRemoveShouldWork() {
        CustomList<String> list = new CustomArrayList<>();
        list.add(-1, "A");
        list.add(-1, "B");
        list.add(-1, "C");

        var iterator = list.iterator();
        iterator.next(); // A
        iterator.remove();

        Assertions.assertEquals(2, list.size());
        Assertions.assertEquals("B", list.get(0));
        Assertions.assertEquals("C", list.get(1));
    }

    @Test
    void iteratorRemoveWithoutNextShouldThrow() {
        CustomList<String> list = new CustomArrayList<>();
        list.add(-1, "A");

        var iterator = list.iterator();
        Assertions.assertThrows(IllegalStateException.class, iterator::remove);
    }

    @Test
    void iteratorDoubleRemoveShouldThrow() {
        CustomList<String> list = new CustomArrayList<>();
        list.add(-1, "A");

        var iterator = list.iterator();
        iterator.next();
        iterator.remove();
        Assertions.assertThrows(IllegalStateException.class, iterator::remove);
    }

    @Test
    void streamOperationsShouldWork() {
        CustomList<Integer> list = new CustomArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(-1, i);
        }

        List<Integer> result = list.stream()
                .filter(x -> x % 2 == 0)
                .map(x -> x * 2)
                .collect(Collectors.toList());

        Assertions.assertEquals(List.of(0, 4, 8, 12, 16), result);
    }

    @Test
    void addAllShouldAddAllElements() {
        CustomList<String> list = new CustomArrayList<>();
        List<String> toAdd = List.of("A", "B", "C");

        for (String s : toAdd) {
            list.add(-1, s);
        }

        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals("A", list.get(0));
        Assertions.assertEquals("B", list.get(1));
        Assertions.assertEquals("C", list.get(2));
    }

    @Test
    void largeNumberOfElementsShouldWork() {
        CustomList<Integer> list = new CustomArrayList<>();
        int count = 10000;

        for (int i = 0; i < count; i++) {
            list.add(-1, i);
        }

        Assertions.assertEquals(count, list.size());
        for (int i = 0; i < count; i++) {
            Assertions.assertEquals(i, list.get(i));
        }
    }

    @Test
    void clearShouldRemoveAllElements() {
        CustomList<String> list = new CustomArrayList<>();
        list.add(-1, "A");
        list.add(-1, "B");
        list.add(-1, "C");

        list.clear();
        Assertions.assertEquals(0, list.size());
        Assertions.assertTrue(list.isEmpty());
        list.add(-1, "D");
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals("D", list.get(0));
    }

    @Test
    void equalsAndHashCodeShouldWork() {
        CustomList<String> list1 = new CustomArrayList<>();
        list1.add(-1, "A");
        list1.add(-1, "B");

        CustomList<String> list2 = new CustomArrayList<>();
        list2.add(-1, "A");
        list2.add(-1, "B");

        Assertions.assertEquals(list1, list2);

        Assertions.assertEquals(list1.hashCode(), list2.hashCode());

        list2.set(1, "C");
        Assertions.assertNotEquals(list1, list2);
    }

    @Test
    void toStringShouldReturnProperRepresentation() {
        CustomList<String> list = new CustomArrayList<>();
        list.add(-1, "A");
        list.add(-1, "B");
        list.add(-1, "C");

        String str = list.toString();
        Assertions.assertTrue(str.contains("A"));
        Assertions.assertTrue(str.contains("B"));
        Assertions.assertTrue(str.contains("C"));
    }
}