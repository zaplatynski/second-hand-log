package de.marza.firstspirit.modules.logging.console;


import java.util.Arrays;
import java.util.List;

/**
 * The enum Menu actions.
 */
public enum MenuActions {

  /**
   * Close menu actions.
   */
  CLOSE,

  /**
   * Copy log menu actions.
   */
  COPY_LOG,

  /**
   * Cut log menu actions.
   */
  CUT_LOG,

  /**
   * Clear log menu actions.
   */
  CLEAR_LOG,

  /**
   * Show logInfo menu actions.
   */
  SHOW_INFO,

  /**
   * Show help contents menu actions.
   */
  SHOW_HELP_CONTENTS,

  /**
   * Show bugs features menu actions.
   */
  SHOW_BUGS_FEATURES,

  /**
   * Show log lines 100 menu actions.
   */
  SHOW_LOG_LINES_100(100),

  /**
   * Show log lines 500 menu actions.
   */
  SHOW_LOG_LINES_500(500),

  /**
   * Show log lines 1 k menu actions.
   */
  SHOW_LOG_LINES_1K(1000),

  /**
   * Show log lines 2 k menu actions.
   */
  SHOW_LOG_LINES_2K(2000),

  /**
   * Show log lines 5 k menu actions.
   */
  SHOW_LOG_LINES_5K(5000),

  /**
   * Show log lines 10 k menu actions.
   */
  SHOW_LOG_LINES_10K(10000);

  private final int lines;

  MenuActions() {
    this.lines = 0;
  }

  MenuActions(final int lines) {
    this.lines = lines;
  }

  /**
   * Log lines list.
   *
   * @return the list
   */
  public static List<MenuActions> logLines() {
    return Arrays.asList(SHOW_LOG_LINES_100,
        SHOW_LOG_LINES_500,
        SHOW_LOG_LINES_1K,
        SHOW_LOG_LINES_2K,
        SHOW_LOG_LINES_5K,
        SHOW_LOG_LINES_10K);
  }

  /**
   * Gets lines.
   *
   * @return the lines
   */
  public int getLines() {
    return lines;
  }
}
