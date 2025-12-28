package org.university.feature.ui.option;

import org.university.common.Constants;

public enum DataProcessOption implements CodeOption {
    FIND_STUDENT(1, Constants.DESCRIPTION_FIND_STUDENT),
    SORT_STUDENTS(2, Constants.DESCRIPTION_SORT_STUDENTS),
    SHOW_DATA_TO_USER(3, Constants.DESCRIPTION_SHOW_DATA),
    SAVE_DATA_TO_FILE(4, Constants.DESCRIPTION_SAVE_DATA),
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

}
