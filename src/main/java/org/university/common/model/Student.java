package org.university.common.model;

import java.util.Objects;

public class Student {
    public final String groupNumber;
    public final double averageScore;
    public final String recordBookNumber;

    private Student(Builder builder) {
        groupNumber = builder.groupNumber;
        averageScore = builder.averageScore;
        recordBookNumber = builder.recordBookNumber;
    }

    public static class Builder {
        private String groupNumber = "1";
        private double averageScore = 0;
        private String recordBookNumber = "1";

        public Builder groupNumber(String groupNumber) {
            this.groupNumber = groupNumber;
            return this;
        }

        public Builder averageScore(double averageScore) {
            this.averageScore = averageScore;
            return this;
        }

        public Builder recordBookNumber(String recordBookNumber) {
            this.recordBookNumber = recordBookNumber;
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
        return "Student{" +
                "groupNumber='" + groupNumber + '\'' +
                ", averageScore=" + averageScore +
                ", recordBookNumber='" + recordBookNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Double.compare(averageScore, student.averageScore) == 0 &&
                Objects.equals(groupNumber, student.groupNumber) &&
                Objects.equals(recordBookNumber, student.recordBookNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupNumber, averageScore, recordBookNumber);
    }
}
