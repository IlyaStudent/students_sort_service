package org.university.common;

public final class Constants {

    private Constants() {
    }

    public static final int DEFAULT_CAPACITY = 10;

    public static final String NONE_STRING = "None";

    // Sorting names
    public static final String BUBBLE_SORT_NAME = "Пузырьковая сортировка";
    public static final String QUICK_SORT_NAME = "Быстрая сортировка";
    public static final String MERGE_SORT_NAME = "Сортировка слиянием";
    public static final String EVEN_ODD_BUBBLE_SORT_NAME = "Четно-нечетная пузырьковая сортировка";
    public static final String EVEN_ODD_QUICK_SORT_NAME = "Четно-нечетная быстрая сортировка";
    public static final String EVEN_ODD_MERGE_SORT_NAME = "Четно-нечетная сортировка слиянием";

    // Exception messages
    public static final String LIST_CANNOT_BE_NULL = "List cannot be null";
    public static final String COMPARATOR_CANNOT_BE_NULL = "Comparator cannot be null";
    public static final String EVEN_ODD_SORT_EXCEPTION = "EvenOdd sort works only with Student objects";

}
