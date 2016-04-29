package de.marza.firstspirit.modules.logging.console;


import java.util.Arrays;
import java.util.List;

public enum MenuActions {

    CLOSE,
    COPY_LOG,
    CUT_LOG,
    CLEAR_LOG,
    SHOW_INFO,
    SHOW_HELP_CONTENTS,
    SHOW_BUGS_FEATURES,
    SHOW_LOG_LINES_100(100),
    SHOW_LOG_LINES_500(500),
    SHOW_LOG_LINES_1K(1000),
    SHOW_LOG_LINES_2K(2000),
    SHOW_LOG_LINES_5K(5000),
    SHOW_LOG_LINES_10K(10000);

    private final int lines;

    MenuActions() {
        this.lines = 0;
    }

    MenuActions(final int lines) {
        this.lines = lines;
    }

    public int getLines() {
        return lines;
    }

    public static List<MenuActions> logLines() {
        return Arrays.asList(SHOW_LOG_LINES_100,
                SHOW_LOG_LINES_500,
                SHOW_LOG_LINES_1K,
                SHOW_LOG_LINES_2K,
                SHOW_LOG_LINES_5K,
                SHOW_LOG_LINES_10K);
    }
}
