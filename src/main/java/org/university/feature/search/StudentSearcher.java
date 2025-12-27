package org.university.feature.search;

import org.university.common.collection.CustomList;
import org.university.common.model.Student;

public interface StudentSearcher {
    int countOccurrences(CustomList<Student> collection, String findGroupNumber);
}
