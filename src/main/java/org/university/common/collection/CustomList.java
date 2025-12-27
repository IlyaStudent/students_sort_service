package org.university.common.collection;

import java.util.stream.Stream;

public interface CustomList<T> extends Iterable<T> {

    void add(T element);

    void add(int i, T element);

    T get(int index);

    void set(int index, T element);

    T remove(int index);

    int size();

    boolean isEmpty();

    void clear();

    Stream<T> stream();

    T[] toArray(T[] a);

    int lastIndexOf(T b);

    int indexOf(T o);

    boolean contains(T a);
}