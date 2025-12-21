package org.university.feature.data.generator;

import org.university.common.collection.CustomList;
import org.university.common.model.Student;

public interface DataGenerator {
    CustomList<Student> generateStudents(int count);
}
