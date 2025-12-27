package org.university.feature.data.manualinput;

import org.university.common.collection.CustomList;
import org.university.common.model.Student;

import java.util.InputMismatchException;

public interface ManualInput {
    CustomList<Student> inputData(int count) throws InputMismatchException;
    String getInputType();
}
