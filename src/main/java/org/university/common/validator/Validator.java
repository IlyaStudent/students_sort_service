package org.university.common.validator;

public interface Validator<T> {
    void validate(T item);
}
