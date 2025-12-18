package org.university.common.collection;

import org.university.common.Constants;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CustomArrayList<T> implements CustomList<T> {

    private Object[] elementData;
    private int size;

    private int modCount;

    public CustomArrayList() {
        this.elementData = new Object[Constants.DEFAULT_CAPACITY];
        this.size = 0;
    }

    public CustomArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("initialCapacity must be >= 0");
        }

        int capacity = (initialCapacity == 0) ? Constants.DEFAULT_CAPACITY : initialCapacity;
        this.elementData = new Object[capacity];
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

        elementData[--size] = null;
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
        Arrays.fill(elementData, 0, size, null);
        size = 0;
        modCount++;
    }

    @Override
    public Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        return (T[]) Arrays.copyOf(elementData, size);
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
        int newCapacity = oldCapacity * 2;

        if (newCapacity < minCapacity) {
            newCapacity = minCapacity;
        }

        elementData = Arrays.copyOf(elementData, newCapacity);
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

    private final class Itr implements Iterator<T> {
        private int cursor;
        private int lastRet = -1;
        private int expectedModCount;

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
            cursor = i + 1;
            lastRet = i;
            return elementAt(i);
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
            expectedModCount = modCount;
        }

        private void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException("List was modified during iteration");
            }
        }
    }
}
