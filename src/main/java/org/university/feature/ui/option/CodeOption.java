package org.university.feature.ui.option;

import java.util.Arrays;
import java.util.Optional;

public interface CodeOption {
    int getCode();
    String getDescription();

    static <T extends Enum<T> & CodeOption> Optional<T> fromCode(int code, T[] values) {
        return Arrays.stream(values)
                .filter(option -> option.getCode() == code)
                .findAny();
    }
}
