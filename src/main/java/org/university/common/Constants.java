package org.university.common;

import java.util.regex.Pattern;

public final class Constants {

    private Constants() {
        throw new AssertionError("Cannot instantiate a Constants class");
    }

    // Collection constants
    public static final int DEFAULT_CAPACITY = 10;

    // Application constants
    public static final String JSON_FILENAME = "students.json";
    public static final String JSON_FILEPATH = "src/main/resources/data/";
    public static final Pattern JSON_FILE_PATTERN =
            Pattern.compile("^[a-zA-Z0-9_.\\-\\s]+?\\.json$");

    // Student data constants
    public static final String[] GROUP_PREFIXES = {
            "CS", "IT", "SE", "AI", "CY", "MA", "PH", "BI", "EC", "EN",
            "CH", "PH", "ME", "EE", "CE", "BT", "MT", "AT", "NE", "RO"
    };
    public static final double MIN_AVERAGE_SCORE  = 2.0;
    public static final double MAX_AVERAGE_SCORE  = 5.0;

    // Validation patterns
    public static final String PATTERN_GROUP_NUMBER = "[A-Z]{2}-\\d{3}";
    public static final String PATTERN_RECORD_BOOK = "\\d{4}-\\d{5}";

    // Data format strings
    public static final String FORMAT_GROUP_NUMBER = "%s-%03d";
    public static final String FORMAT_RECORD_BOOK = "%d-%05d";
    public static final String FORMAT_STUDENT_DISPLAY = "Студент - { Номер группы - [%s], Средний балл - [%.2f], Номер зачетной книжки - [%s] }";

    // Thread timeout constants
    public static final long SEARCH_TIMEOUT_SECONDS = 30;

    // Sorting names
    public static final String BUBBLE_SORT_NAME = "Пузырьковая сортировка";
    public static final String QUICK_SORT_NAME = "Быстрая сортировка";
    public static final String MERGE_SORT_NAME = "Сортировка слиянием";
    public static final String EVEN_ODD_BUBBLE_SORT_NAME = "Четно-нечетная пузырьковая сортировка";
    public static final String EVEN_ODD_QUICK_SORT_NAME = "Четно-нечетная быстрая сортировка";
    public static final String EVEN_ODD_MERGE_SORT_NAME = "Четно-нечетная сортировка слиянием";

    // Validation error messages
    public static final String ERROR_GROUP_NUMBER_EMPTY = "Group number cannot be empty";
    public static final String ERROR_GROUP_NUMBER_INVALID = "Group number field value is invalid ";
    public static final String ERROR_RECORD_BOOK_EMPTY = "Record book cannot be empty";
    public static final String ERROR_RECORD_BOOK_INVALID = "Record book number field value is invalid ";
    public static final String ERROR_AVERAGE_SCORE_INVALID = "Average score field value is invalid ";
    public static final String ERROR_LIST_EMPTY = "List cannot be empty";
    public static final String ERROR_STUDENT_NULL = "Student cannot be null";

    // UI messages
    public static final String MESSAGE_INVALID_INPUT = "Некорректный формат ввода. Попробуйте еще раз.";
    public static final String MESSAGE_INVALID_INTEGER = "\nНеверный ввод. Пожалуйста, введите допустимое целое число.";

    // UI prompts
    public static final String PROMPT_GROUP_NUMBER = "Введите номер группы (формат: XX-NNN, пример: CS-101): ";
    public static final String PROMPT_AVERAGE_SCORE = "Введите средний балл (2.0 - 5.0): ";
    public static final String PROMPT_RECORD_BOOK = "Введите номер зачетной книжки (формат: YYYY-NNNNN, пример: 2023-12345): ";
    public static final String PROMPT_TRY_AGAIN = "Попробуйте еще раз: ";
    public static final String PROMPT_STUDENT_NUMBER = "\nПожалуйста введите данные для студента под номером #%d:%n";

    // UI option descriptions
    public static final String DESCRIPTION_OPTION_BACK = "Назад";
    public static final String DESCRIPTION_OPTION_EXIT = "Выход";
    public static final String DESCRIPTION_SORT_ALL_FIELDS = "Отсортировать объекты по всем полям";
    public static final String DESCRIPTION_SORT_ALL_EVEN_VALUES_BY_NUMBER_FIELD =
            "Отсортировать объекты с четными значениями по среднему баллу";

    // UI menu titles
    public static final String TITLE_LOAD_OPTION = "\n=== Варианты получения данных ===\n";
    public static final String TITLE_DATA_PROCESS_OPTION = "\n=== Варианты обработки данных ===\n";
    public static final String TITLE_SORT_ALGORITHM_OPTION = "\n=== Варианты алгоритмов сортировки ===\n";
    public static final String TITLE_SPECIFIC_SORT_OPTION = "\n=== Конкретные сортировки ===\n";

    // UI templates
    public static final String TEMPLATE_OUTPUT_OPTION = "%d. %s%n";
    public static final String TEMPLATE_INPUT_NUMBER_OPTION = "Введите номер варианта (от %d до %d): ";

    // UI messages - data loading
    public static final String MESSAGE_DATA_LOADED = "Данные получены....";
    public static final String MESSAGE_INVALID_FORMAT_INPUT = "Некорректный формат ввода.\n";
    public static final String MESSAGE_DATA_EXCEEDS_FILE_SIZE = "\nКоличество запрашиваемых данных превышает целевой размер файла.";
    public static final String MESSAGE_TRY_AGAIN = "Попробуйте снова.\n";
    public static final String MESSAGE_TRY_AGAIN_NEWLINE = "Попробуйте еще раз.\n";
    public static final String MESSAGE_INVALID_OPTION = "Вы указали вариант, который не существует. Попробуйте еще раз.";
    public static final String MESSAGE_INVALID_COUNT = "Количество записей не может быть отрицательным или равное 0.";
    public static final String MESSAGE_INVALID_CONTINUE_INPUT = "Некорректный ввод. Попробуйте еще раз.";

    // UI prompts - additional
    public static final String PROMPT_CONTINUE_INPUT = "Возобновить ввод? (Да|Нет): ";
    public static final String PROMPT_NUMBER_OF_RECORDS = "Введите количество записей: ";

    // UI input values
    public static final String INPUT_YES = "да";
    public static final String INPUT_NO = "нет";

    // MenuHandler messages
    public static final String MESSAGE_FINISH_PROGRAM = "Завершение программы...";
    public static final String MESSAGE_SYSTEM_TITLE = "\n=== Система сортировки студентов ===";
    public static final String MESSAGE_RETURN_TO_LOAD_OPTIONS = "Возврат к выбору вариантов получения данных...";
    public static final String MESSAGE_MATCHES_COUNT = "Количество совпадений: ";
    public static final String MESSAGE_DATA_SORTED = "Данные отсортированы...";
    public static final String MESSAGE_UNEXPECTED_ERROR = "Unexpected error: ";

    // DataLoadOption descriptions
    public static final String DESCRIPTION_LOAD_CONSOLE = "Ввести вручную";
    public static final String DESCRIPTION_LOAD_FILE = "Загрузить из файла";
    public static final String DESCRIPTION_LOAD_GENERATION = "Сгенерировать случайным образом";

    // DataProcessOption descriptions
    public static final String DESCRIPTION_FIND_STUDENT = "Поиск студентов по номеру группы";
    public static final String DESCRIPTION_SORT_STUDENTS = "Отсортировать студентов";
    public static final String DESCRIPTION_SHOW_DATA = "Отобразить данные студентов";
    public static final String DESCRIPTION_SAVE_DATA = "Записать данные студентов в файл";

    // ManualInput messages
    public static final String MESSAGE_DUPLICATE_RECORD_BOOK = "\nСтудент с номером зачетки %s уже существует, повторите ввод\n";
}
