package org.university.common.model;

import org.university.common.Constants;

import java.util.Objects;

public class Student implements Comparable<Student> {
    private final String groupNumber;
    private final double averageScore;
    private final String recordBookNumber;

    private Student(Builder builder) {
        groupNumber = builder.groupNumber;
        averageScore = builder.averageScore;
        recordBookNumber = builder.recordBookNumber;
    }

    @Override
    public int compareTo(Student other) {
        Objects.requireNonNull(other, "Student to compare cannot be null");
        int result = this.groupNumber.compareTo(other.groupNumber);
        if (result != 0) {
            return result;
        }

        result = Double.compare(this.averageScore, other.averageScore);
        if (result != 0) {
            return result;
        }

        return this.recordBookNumber.compareTo(other.recordBookNumber);
    }

    public static class Builder {
        private String groupNumber = "AA-000";
        private double averageScore = 5.0;
        private String recordBookNumber = "2000-00000";

        public Builder groupNumber(String groupNumber) {
            this.groupNumber = Objects.requireNonNull(groupNumber, "Group number cannot be null");
            return this;
        }

        public Builder averageScore(double averageScore) {
            this.averageScore = averageScore;
            return this;
        }

        public Builder recordBookNumber(String recordBookNumber) {
            this.recordBookNumber = Objects.requireNonNull(recordBookNumber, "Record book number cannot be null");
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public String getRecordBookNumber() {
        return recordBookNumber;
    }

    @Override
    public String toString() {
        return String.format(
                Constants.FORMAT_STUDENT_DISPLAY,
                groupNumber, averageScore, recordBookNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Double.compare(averageScore, student.averageScore) == 0
                && Objects.equals(groupNumber, student.groupNumber)
                && Objects.equals(recordBookNumber, student.recordBookNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupNumber, averageScore, recordBookNumber);
    }
}
