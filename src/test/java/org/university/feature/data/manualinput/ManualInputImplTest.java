package org.university.feature.data.manualinput;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.university.common.collection.CustomList;
import org.university.common.model.Student;
import org.university.feature.ui.io.InputReader;
import org.university.feature.ui.io.OutputWriter;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class ManualInputImplTest {

    private ManualInputImpl manualInput;
    private TestInputReader testReader;
    private TestOutputWriter testWriter;
    private PrintStream originalOut;
    private InputStream originalIn;

    @BeforeEach
    void setUp() {
        originalIn = System.in;
        originalOut = System.out;

        testReader = new TestInputReader();
        testWriter = new TestOutputWriter();
        manualInput = new ManualInputImpl(testReader, testWriter);
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    void inputData_ShouldReturnCorrectNumberOfStudents() {
        int count = 3;

        testReader.addInput("AB-123")
                .addInput("4.5")
                .addInput("2023-12345")
                .addInput("CD-456")
                .addInput("3.8")
                .addInput("2023-54321")
                .addInput("EF-789")
                .addInput("4.2")
                .addInput("2023-98765");

        CustomList<Student> result = manualInput.inputData(count);

        assertNotNull(result);
        assertEquals(count, result.size());

        Student student1 = result.get(0);
        assertEquals("AB-123", student1.getGroupNumber());
        assertEquals(4.5, student1.getAverageScore(), 0.001);
        assertEquals("2023-12345", student1.getRecordBookNumber());

        Student student2 = result.get(1);
        assertEquals("CD-456", student2.getGroupNumber());
        assertEquals(3.8, student2.getAverageScore(), 0.001);
        assertEquals("2023-54321", student2.getRecordBookNumber());

        Student student3 = result.get(2);
        assertEquals("EF-789", student3.getGroupNumber());
        assertEquals(4.2, student3.getAverageScore(), 0.001);
        assertEquals("2023-98765", student3.getRecordBookNumber());

        String output = testWriter.getOutput();
        assertTrue(output.contains("студента"));
        assertTrue(output.contains("номер группы"));
        assertTrue(output.contains("средний балл"));
        assertTrue(output.contains("зачетной книжки"));
    }

    @Test
    void inputData_ShouldHandleDuplicateRecordBookNumbers() {
        int count = 2;

        testReader.addInput("CS-101")
                .addInput("4.5")
                .addInput("2023-12345")
                .addInput("IT-202")
                .addInput("3.8")
                .addInput("2023-12345")
                .addInput("2023-54321");

        CustomList<Student> result = manualInput.inputData(count);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("2023-54321", result.get(1).getRecordBookNumber());

        String output = testWriter.getOutput();
        assertTrue(output.contains("2023-12345"));
    }

    @Test
    void inputData_ShouldHandleSingleStudent() {
        int count = 1;

        testReader.addInput("AB-123")
                .addInput("4.5")
                .addInput("2023-12345");

        CustomList<Student> result = manualInput.inputData(count);

        assertNotNull(result);
        assertEquals(1, result.size());

        Student student = result.get(0);
        assertEquals("AB-123", student.getGroupNumber());
        assertEquals(4.5, student.getAverageScore(), 0.001);
        assertEquals("2023-12345", student.getRecordBookNumber());
    }

    @Test
    void inputData_ShouldConvertGroupNumberToUpperCase() {
        int count = 1;

        testReader.addInput("ab-123")
                .addInput("4.5")
                .addInput("2023-12345");

        CustomList<Student> result = manualInput.inputData(count);

        assertEquals("AB-123", result.get(0).getGroupNumber());
    }

    @Test
    void inputData_ShouldReturnEmptyList_WhenCountIsZero() {
        int count = 0;

        CustomList<Student> result = manualInput.inputData(count);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        assertTrue(testReader.getInputQueue().isEmpty());
    }

    @Test
    void getInputType_ShouldReturnCorrectClassName() {
        String inputType = manualInput.getInputType();

        assertEquals(ManualInputImpl.class.getName(), inputType);
    }

    @Test
    void inputData_ShouldPreserveOrderOfStudents() {
        int count = 3;

        testReader.addInput("AB-123")
                .addInput("4.5")
                .addInput("2023-00001")
                .addInput("CD-456")
                .addInput("3.8")
                .addInput("2023-00002")
                .addInput("EF-789")
                .addInput("4.2")
                .addInput("2023-00003");

        CustomList<Student> result = manualInput.inputData(count);

        assertEquals(3, result.size());
        assertEquals("AB-123", result.get(0).getGroupNumber());
        assertEquals("CD-456", result.get(1).getGroupNumber());
        assertEquals("EF-789", result.get(2).getGroupNumber());
    }

    @Test
    void inputData_ShouldCallWriterMethodsCorrectly() {
        int count = 1;

        testReader.addInput("AB-123")
                .addInput("4.5")
                .addInput("2023-12345");

        manualInput.inputData(count);

        String output = testWriter.getOutput();
        assertTrue(output.contains("студента"));
        assertTrue(output.contains("номер группы"));
        assertTrue(output.contains("средний балл"));
        assertTrue(output.contains("зачетной книжки"));
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
            return readInput().toUpperCase();
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