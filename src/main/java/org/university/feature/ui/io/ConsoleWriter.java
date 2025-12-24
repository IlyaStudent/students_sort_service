package org.university.feature.ui.io;

import java.io.PrintWriter;

public class ConsoleWriter implements OutputWriter {
    private final PrintWriter out;

    public ConsoleWriter() {
        out = new PrintWriter(System.out, true);
    }

    public void println(String message) {
        out.println(message);
    }

    public void print(String text) {
        out.print(text);
        out.flush();
    }

    public void printf(String format, Object ... args) {
        out.printf(format, args);
        out.flush();
    }

    @Override
    public void close() {
        out.close();
    }
}
