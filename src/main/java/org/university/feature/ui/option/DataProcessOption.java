package org.university.feature.ui.option;

import org.university.common.Constants;

import java.util.Optional;

public enum DataProcessOption implements CodeOption {
    FIND_STUDENT(1, "Поиск студентов по номеру группы"),
    SORT_STUDENTS(2, "Отсортировать студентов"),
    SHOW_DATA_TO_USER(3, "Отобразить данные студентов"),
    SAVE_DATA_TO_FILE(4, "Записать данные студентов в файл"),
    BACK(5, Constants.DESCRIPTION_OPTION_BACK),
    EXIT(6, Constants.DESCRIPTION_OPTION_EXIT);

    private final int code;
    private final String description;

    DataProcessOption(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public static Optional<DataProcessOption> fromCode(int code) {
        return CodeOption.fromCode(code, values());
    }
}
