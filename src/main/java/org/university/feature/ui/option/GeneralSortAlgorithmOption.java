package org.university.feature.ui.option;

import org.university.common.Constants;

import java.util.Optional;

public enum GeneralSortAlgorithmOption implements CodeOption {
    BUBBLE_SORT(1, Constants.BUBBLE_SORT_NAME),
    MERGE_SORT(2, Constants.MERGE_SORT_NAME),
    QUICK_SORT(3, Constants.QUICK_SORT_NAME),
    BACK(4, Constants.DESCRIPTION_OPTION_BACK),
    EXIT(5, Constants.DESCRIPTION_OPTION_EXIT);

    private final int code;
    private final String description;

    GeneralSortAlgorithmOption(int code, String description) {
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

    public static Optional<GeneralSortAlgorithmOption> fromCode(int code) {
        return CodeOption.fromCode(code, values());
    }
}
