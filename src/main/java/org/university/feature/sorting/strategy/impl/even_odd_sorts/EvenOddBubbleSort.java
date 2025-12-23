package org.university.feature.sorting.strategy.impl.even_odd_sorts;


import org.university.common.Constants;
import org.university.common.collection.CustomArrayList;
import org.university.common.collection.CustomList;
import org.university.common.model.Student;
import org.university.feature.sorting.strategy.SortStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EvenOddBubbleSort implements SortStrategy {


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

        bubbleSort(evenStudents, comparator);

        for (int i = 0; i < evenIndices.size(); i++) {
            int position = evenIndices.get(i);
            Student sortedStudent = evenStudents.get(i);
            list.set(position, sortedStudent);
        }
    }

    private void bubbleSort(CustomList<Student> list, Comparator<Student> comparator) {
        int n = list.size();

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (comparator.compare(list.get(j), list.get(j + 1)) > 0) {
                    Student temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                    swapped = true;
                }
            }

            if (!swapped) break;
        }
    }

    private boolean isEven(double averageScore) {
        int intPart = (int) averageScore;
        return intPart % 2 == 0;
    }

    @Override
    public String getName() {
        return Constants.BUBBLE_SORT_NAME;
    }
}
