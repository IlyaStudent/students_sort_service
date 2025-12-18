package org.university.common.collection;

import java.lang.reflect.Array;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CustomArrayList<T> implements CustomList<T> {

    private static final int DEFAULT_CAPACITY = 10;

    private Object[] elementData;
    private int size;

    // Для fail-fast iterator
    private int modCount;

    public CustomArrayList() {
        this.elementData = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public CustomArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("initialCapacity must be >= 0");
        }
        this.elementData = new Object[Math.max(initialCapacity, DEFAULT_CAPACITY)];
        this.size = 0;
    }

    @Override
    public void add(T element) {
        ensureCapacityInternal(size + 1);
        elementData[size++] = element;
        modCount++;
    }

    @Override
    public T get(int index) {
        rangeCheck(index);
        return elementAt(index);
    }

    @Override
    public void set(int index, T element) {
        rangeCheck(index);
        elementData[index] = element;
        modCount++;
    }

    @Override
    public T remove(int index) {
        rangeCheck(index);
        T oldValue = elementAt(index);

        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }

        elementData[--size] = null; // help GC
        modCount++;
        return oldValue;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        if (size == 0) {
            return;
        }
        for (int i = 0; i < size; i++) {
            elementData[i] = null;
        }
        size = 0;
        modCount++;
    }

    @Override
    public Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    @Override
    public T[] toArray() {
        Class<?> componentType = findFirstNonNullComponentType();
        @SuppressWarnings("unchecked")
        T[] result = (T[]) Array.newInstance(componentType, size);

        for (int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked")
            T value = (T) elementData[i];
            result[i] = value;
        }
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    @Override
    public Spliterator<T> spliterator() {
        return Spliterators.spliterator(iterator(), size, Spliterator.ORDERED);
    }

    private void ensureCapacityInternal(int minCapacity) {
        if (minCapacity <= elementData.length) {
            return;
        }
        grow(minCapacity);
    }

    private void grow(int minCapacity) {
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1); // * 1.5
        if (newCapacity < minCapacity) {
            newCapacity = minCapacity;
        }
        Object[] newArray = new Object[newCapacity];
        System.arraycopy(elementData, 0, newArray, 0, size);
        elementData = newArray;
    }

    private void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) elementData[index];
    }

    private Class<?> findFirstNonNullComponentType() {
        for (int i = 0; i < size; i++) {
            Object v = elementData[i];
            if (v != null) {
                return v.getClass();
            }
        }
        return Object.class; // если все null или список пустой
    }

    private final class Itr implements Iterator<T> {
        private int cursor;
        private int lastRet = -1;
        private final int expectedModCount;

        private Itr() {
            this.expectedModCount = modCount;
        }

        @Override
        public boolean hasNext() {
            checkForComodification();
            return cursor != size;
        }

        @Override
        public T next() {
            checkForComodification();
            int i = cursor;
            if (i >= size) {
                throw new NoSuchElementException();
            }
            Object[] elementData = CustomArrayList.this.elementData;
            cursor = i + 1;
            lastRet = i;
            @SuppressWarnings("unchecked")
            T value = (T) elementData[i];
            return value;
        }

        @Override
        public void remove() {
            checkForComodification();
            if (lastRet < 0) {
                throw new IllegalStateException("next() has not been called yet");
            }
            CustomArrayList.this.remove(lastRet);
            cursor = lastRet;
            lastRet = -1;
        }

        private void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException("List was modified during iteration");
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CustomArrayList{size=");
        sb.append(size).append(", elements=[");
        for (int i = 0; i < size; i++) {
            sb.append(Objects.toString(elementData[i]));
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]}");
        return sb.toString();
    }
}
