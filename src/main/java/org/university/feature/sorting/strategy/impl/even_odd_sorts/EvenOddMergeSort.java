package org.university.feature.sorting.strategy.impl.even_odd_sorts;


import org.university.common.Constants;
import org.university.common.collection.CustomArrayList;
import org.university.common.collection.CustomList;
import org.university.common.model.Student;
import org.university.feature.sorting.strategy.SortStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EvenOddMergeSort implements SortStrategy {

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
            mergeSort(evenStudents, 0, evenStudents.size() - 1, comparator);
        }

        for (int i = 0; i < evenIndices.size(); i++) {
            list.set(evenIndices.get(i), evenStudents.get(i));
        }
    }

    private void mergeSort(CustomList<Student> list, int left, int right, Comparator<Student> comparator) {
        if (left < right) {
            int middle = left + (right - left) / 2;

            mergeSort(list, left, middle, comparator);
            mergeSort(list, middle + 1, right, comparator);

            merge(list, left, middle, right, comparator);
        }
    }

    private void merge(CustomList<Student> list, int left, int middle, int right, Comparator<Student> comparator) {
        CustomList<Student> leftList = new CustomArrayList<>();
        CustomList<Student> rightList = new CustomArrayList<>();

        for (int i = left; i <= middle; i++) {
            leftList.add(list.get(i));
        }
        for (int i = middle + 1; i <= right; i++) {
            rightList.add(list.get(i));
        }

        int i = 0, j = 0, k = left;

        while (i < leftList.size() && j < rightList.size()) {
            if (comparator.compare(leftList.get(i), rightList.get(j)) <= 0) {
                list.set(k, leftList.get(i));
                i++;
            } else {
                list.set(k, rightList.get(j));
                j++;
            }
            k++;
        }

        while (i < leftList.size()) {
            list.set(k, leftList.get(i));
            i++;
            k++;
        }

        while (j < rightList.size()) {
            list.set(k, rightList.get(j));
            j++;
            k++;
        }
    }

    private boolean isEven(double averageScore) {
        return ((int) averageScore) % 2 == 0;
    }

    @Override
    public String getName() {
        return Constants.EVEN_ODD_MERGE_SORT_NAME;
    }
}
