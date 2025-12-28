package org.university.feature.ui.option;

import org.university.common.Constants;

public enum DataLoadOption implements CodeOption {
    CONSOLE(1, Constants.DESCRIPTION_LOAD_CONSOLE),
    FILE(2, Constants.DESCRIPTION_LOAD_FILE),
    GENERATION(3, Constants.DESCRIPTION_LOAD_GENERATION),
    EXIT(4, Constants.DESCRIPTION_OPTION_EXIT);

    private final int code;
    private final String description;

    DataLoadOption(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getCode() {
        return code;
    }

}
