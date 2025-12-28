package org.university.feature.data.loader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.university.common.collection.CustomList;
import org.university.common.model.Student;
import org.university.feature.ui.io.InputReader;
import org.university.feature.ui.io.OutputWriter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class ManualDataLoaderTest {

    private ManualDataLoader manualDataLoader;
    private TestInputReader testReader;
    private TestOutputWriter testWriter;

    @BeforeEach
    void setUp() {
        testReader = new TestInputReader();
        testWriter = new TestOutputWriter();
        manualDataLoader = new ManualDataLoader(testReader, testWriter);
    }

    @Test
    void loadData_ShouldReturnStudents_WhenValidInput() {
        int count = 2;

        testReader.addInput("CS-101")
                .addInput("4.5")
                .addInput("2023-12345")
                .addInput("IT-202")
                .addInput("3.8")
                .addInput("2023-54321");

        CustomList<Student> result = manualDataLoader.loadData(count);

        assertNotNull(result);
        assertEquals(count, result.size());

        Student student1 = result.get(0);
        assertEquals("CS-101", student1.getGroupNumber());
        assertEquals(4.5, student1.getAverageScore(), 0.001);
        assertEquals("2023-12345", student1.getRecordBookNumber());

        Student student2 = result.get(1);
        assertEquals("IT-202", student2.getGroupNumber());
        assertEquals(3.8, student2.getAverageScore(), 0.001);
        assertEquals("2023-54321", student2.getRecordBookNumber());

        String output = testWriter.getOutput();
        assertTrue(output.contains("студента"));
        assertTrue(output.contains("номер группы"));
        assertTrue(output.contains("средний балл"));
        assertTrue(output.contains("зачетной книжки"));
    }

    @Test
    void loadData_ShouldHandleDuplicateRecordBookNumbers() {
        int count = 2;

        testReader.addInput("CS-101")
                .addInput("4.5")
                .addInput("2023-12345")
                .addInput("IT-202")
                .addInput("3.8")
                .addInput("2023-12345")
                .addInput("2023-54321");

        CustomList<Student> result = manualDataLoader.loadData(count);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("2023-54321", result.get(1).getRecordBookNumber());

        String output = testWriter.getOutput();
        assertTrue(output.contains("2023-12345"));
    }

    @Test
    void loadData_ShouldReturnEmptyList_WhenCountIsZero() {
        int count = 0;

        CustomList<Student> result = manualDataLoader.loadData(count);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        assertTrue(testReader.getInputQueue().isEmpty());
    }

    @Test
    void getLoaderType_ShouldReturnCorrectClassName() {
        String loaderType = manualDataLoader.getLoaderType();

        assertEquals(ManualDataLoader.class.getName(), loaderType);
    }

    private static class TestInputReader implements InputReader {
        private final Queue<String> inputQueue = new LinkedList<>();

        public TestInputReader addInput(String input) {
            inputQueue.add(input);
            return this;
        }

        public Queue<String> getInputQueue() {
            return new LinkedList<>(inputQueue);
        }

        @Override
        public int readInt() {
            String input = inputQueue.poll();
            if (input == null) {
                throw new RuntimeException("No more test input available");
            }
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid integer input: " + input);
            }
        }

        @Override
        public String readGroupNumber() {
            return readInput();
        }

        @Override
        public String readInput() {
            String input = inputQueue.poll();
            if (input == null) {
                throw new RuntimeException("No more test input available");
            }
            return input;
        }
    }

    private static class TestOutputWriter implements OutputWriter {
        private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        private final PrintStream printStream = new PrintStream(outputStream, true, StandardCharsets.UTF_8);

        public String getOutput() {
            return outputStream.toString(StandardCharsets.UTF_8);
        }

        public void clearOutput() {
            outputStream.reset();
        }

        @Override
        public void println(String message) {
            printStream.println(message);
        }

        @Override
        public void print(String text) {
            printStream.print(text);
        }

        @Override
        public void printf(String format, Object... args) {
            printStream.printf(format, args);
        }
    }
}