package org.university.feature.sorting.strategy.impl.even_odd_sorts;

import org.university.common.Constants;
import org.university.common.collection.CustomArrayList;
import org.university.common.collection.CustomList;
import org.university.common.model.Student;
import org.university.feature.sorting.strategy.SortStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EvenOddQuickSort implements SortStrategy {


    @Override
    @SuppressWarnings("unchecked")
    public <T> void sort(CustomList<T> list, Comparator<T> comparator) {
        if (list == null) {
            throw new IllegalArgumentException(Constants.LIST_CANNOT_BE_NULL);
        }

        if (comparator == null) {
            throw new IllegalArgumentException(Constants.COMPARATOR_CANNOT_BE_NULL);
        }

        if (list.size() <= 1) {
            return;
        }

        if (!(list.get(0) instanceof Student)) {
            throw new IllegalArgumentException(Constants.EVEN_ODD_SORT_EXCEPTION);
        }

        CustomList<Student> studentList = (CustomList<Student>) list;
        Comparator<Student> studentComparator = (Comparator<Student>) comparator;

        sortEvenOdd(studentList, studentComparator);
    }

    private void sortEvenOdd(CustomList<Student> list, Comparator<Student> comparator) {
        List<Integer> evenIndices = new ArrayList<>();
        CustomList<Student> evenStudents = new CustomArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            Student student = list.get(i);
            if (isEven(student.getAverageScore())) {
                evenIndices.add(i);
                evenStudents.add(student);
            }
        }

        if (evenStudents.size() > 1) {
            quickSort(evenStudents, 0, evenStudents.size() - 1, comparator);
        }

        for (int i = 0; i < evenIndices.size(); i++) {
            list.set(evenIndices.get(i), evenStudents.get(i));
        }
    }

    private void quickSort(CustomList<Student> list, int low, int high, Comparator<Student> comparator) {
        if (low < high) {
            int pivotIndex = partition(list, low, high, comparator);
            quickSort(list, low, pivotIndex - 1, comparator);
            quickSort(list, pivotIndex + 1, high, comparator);
        }
    }

    private int partition(CustomList<Student> list, int low, int high, Comparator<Student> comparator) {
        Student pivot = list.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (comparator.compare(list.get(j), pivot) <= 0) {
                i++;
                Student temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }

        Student temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);

        return i + 1;
    }

    private boolean isEven(double averageScore) {
        return ((int) averageScore) % 2 == 0;
    }

    @Override
    public String getName() {
        return Constants.EVEN_ODD_QUICK_SORT_NAME;
    }
}
