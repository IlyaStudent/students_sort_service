package org.university.common.util;

import org.university.common.collection.CustomArrayList;
import org.university.common.collection.CustomList;

import java.util.stream.Collector;

public final class StreamHelper {
    private StreamHelper() {
        throw new AssertionError("Cannot instantiate StreamHelper class");
    }


    public static <T> Collector<T, ?, CustomList<T>> toCustomList() {
        return Collector.of(
                CustomArrayList::new,
                CustomList::add,
                StreamHelper::combineCustomLists,
                Collector.Characteristics.IDENTITY_FINISH
        );
    }

    private static <T> CustomList<T> combineCustomLists(CustomList<T> list1, CustomList<T> list2) {
        for (T item : list2) {
            list1.add(item);
        }
        return list1;
    }
}
