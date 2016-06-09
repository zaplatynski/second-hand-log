package de.marza.firstspirit.modules.logging.console.utilities;

import java.awt.Color;
import java.util.Locale;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;


/**
 * The enum Level.
 */
public enum Level {

  /**
   * The Error Level.
   */
  ERROR(Color.RED, true),

  /**
   * The Warn Level.
   */
  WARN(Color.ORANGE, true),

  /**
   * The Info Level.
   */
  INFO(Color.BLUE),

  /**
   * The Debug Level.
   */
  DEBUG(Color.DARK_GRAY),

  /**
   * The Default Level.
   */
  DEFAULT(Color.BLACK);


  private final SimpleAttributeSet attributes;
  private final String nameToLowerCase;

  Level(final Color textColor) {
    nameToLowerCase = name().toLowerCase();
    attributes = new SimpleAttributeSet();
    StyleConstants.setForeground(attributes, textColor);
  }

  Level(final Color textColor, final boolean bold) {
    this(textColor);
    StyleConstants.setBold(attributes, bold);
  }

  private static Level findLevel(final String messageFragment, final Level... levels) {
    for (final Level level : levels) {
      if (messageFragment.contains(level.nameToLowerCase())) {
        return level.valueOfFragment(messageFragment);
      }
    }
    return DEFAULT;
  }

  private static boolean isEmpty(final String messageFragment) {
    if (messageFragment == null) {
      return true;
    }
    final String cleanedFragment = messageFragment.trim();
    return cleanedFragment.isEmpty(); //NOPMD
  }

  /**
   * Gets attributes.
   *
   * @return the attributes
   */
  public SimpleAttributeSet getAttributes() {
    return attributes;
  }

  public String nameToLowerCase() {
    return nameToLowerCase;
  }

  /**
   * Value of fragment level.
   *
   * @param messageFragment the message fragment
   * @return the level
   */
  public Level valueOfFragment(final String messageFragment) {
    Level returnLevel = this;
    if (isEmpty(messageFragment)) {
      return this;
    }
    final String lowerCaseFragment = messageFragment.toLowerCase(Locale.getDefault());
    Level level = returnLevel;
    switch (this) {
      case DEBUG:
        level = findLevel(lowerCaseFragment, INFO, WARN, ERROR);
        if (level != DEFAULT) {
          returnLevel = level;
        }
        break;
      case INFO:
        level = findLevel(lowerCaseFragment, WARN, ERROR);
        if (level != DEFAULT) {
          returnLevel = level;
        }
        break;
      case WARN:
        final String errorToLowerCase = ERROR.nameToLowerCase();
        if (lowerCaseFragment.contains(errorToLowerCase)) { //NOPMD
          returnLevel = ERROR;
        }
        break;
      case ERROR:
        returnLevel = ERROR;
        break;
      case DEFAULT:
      default:
        returnLevel = findLevel(lowerCaseFragment, DEBUG, INFO, WARN, ERROR);
        break;
    }
    return returnLevel;
  }

}
