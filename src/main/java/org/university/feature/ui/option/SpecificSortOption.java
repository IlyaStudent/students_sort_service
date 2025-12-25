package org.university.feature.ui.option;

import org.university.common.Constants;

import java.util.Optional;

public enum SpecificSortOption implements CodeOption {
    SORT_ALL_FIELDS(1, Constants.DESCRIPTION_SORT_ALL_FIELDS),
    SORT_EVEN_SCORE(2, Constants.DESCRIPTION_SORT_ALL_EVEN_VALUES_BY_NUMBER_FIELD),
    BACK(3, Constants.DESCRIPTION_OPTION_BACK),
    EXIT(4, Constants.DESCRIPTION_OPTION_EXIT);

    private final int code;
    private final String description;

    SpecificSortOption(int code, String description) {
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

    public static Optional<SpecificSortOption> fromCode(int code) {
        return CodeOption.fromCode(code, values());
    }
}
