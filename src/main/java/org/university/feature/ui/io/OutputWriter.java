package org.university.feature.ui.io;

public interface OutputWriter {
    void println(String message);

    void print(String text);

    void printf(String format, Object... args);
}
