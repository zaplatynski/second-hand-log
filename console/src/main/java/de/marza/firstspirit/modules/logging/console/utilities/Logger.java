package de.marza.firstspirit.modules.logging.console.utilities;


import java.io.PrintStream;
import java.util.IllegalFormatException;
import java.util.Locale;


/**
 * The type Logger.
 */
public final class Logger {

  private static final Logger LOG = new Logger(System.out, System.err);
  private final PrintStream info;
  private final PrintStream error;

  /**
   * Instantiates a new Logger.
   *
   * @param info  the logInfo
   * @param error the logError
   */
  private Logger(final PrintStream info, final PrintStream error) {
    if (info == null) {
      throw new IllegalArgumentException("PrintStream logInfo is null");
    }
    if (error == null) {
      throw new IllegalArgumentException("PrintStream logError is null");
    }
    this.info = info;
    this.error = error;
  }

  public static Logger getInstance() {
    return LOG;
  }

  /**
   * Log info message.
   *
   * @param message the message
   */
  public void logInfo(final String message) {
    info.println(message);
  }

  /**
   * Log info message.
   *
   * @param locale the locale
   * @param format the format
   * @param args   the args
   */
  public void logInfo(final Locale locale, final String format, final Object... args) {
    try {
      info.printf(locale, format, args);
    } catch (final IllegalFormatException e) { //NOPMD
      //ignore to avoid stack overflows
    }
  }

  /**
   * Log info message.
   *
   * @param format the format
   * @param args   the args
   */
  public void logInfo(final String format, final Object... args) {
    try {
      info.printf(format, args);
    } catch (final IllegalFormatException e) { //NOPMD
      //ignore to avoid stack overflows
    }
  }

  /**
   * Log error message.
   *
   * @param message the message
   */
  public void logError(final String message) {
    error.println(message);
  }

  /**
   * Log error message.
   *
   * @param locale the locale
   * @param format the format
   * @param args   the args
   */
  public void logError(final Locale locale, final String format, final Object... args) {
    try {
      error.printf(locale, format, args);
    } catch (final IllegalFormatException e) { //NOPMD
      //signore to avoid stack overflows
    }
  }

  /**
   * Log error message.
   *
   * @param format the format
   * @param args   the args
   */
  public void logError(final String format, final Object... args) {
    try {
      error.printf(format, args);
    } catch (final IllegalFormatException e) { //NOPMD
      //ignore to avoid stack overflows
    }
  }
}
