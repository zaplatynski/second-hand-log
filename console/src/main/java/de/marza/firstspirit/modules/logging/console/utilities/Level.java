package de.marza.firstspirit.modules.logging.console.utilities;

import java.awt.Color;

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

  Level(final Color textColor) {
    attributes = new SimpleAttributeSet();
    StyleConstants.setForeground(attributes, textColor);
  }

  Level(final Color textColor, final boolean bold) {
    this(textColor);
    StyleConstants.setBold(attributes, bold);
  }

  private static Level findLevel(final String messageFragment, final Level... levels) {
    for (final Level level : levels) {
      if (messageFragment.contains(level.name())) {
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
    Level level = this;
    switch (this) {
      case DEBUG:
        level = findLevel(messageFragment, INFO, WARN, ERROR);
        if (level != DEFAULT) {
          returnLevel = level;
        }
        break;
      case INFO:
        level = findLevel(messageFragment, WARN, ERROR);
        if (level != DEFAULT) {
          returnLevel = level;
        }
        break;
      case WARN:
        if (messageFragment.contains(ERROR.name())) {
          returnLevel = ERROR;
        }
        break;
      case ERROR:
        returnLevel = ERROR;
        break;
      case DEFAULT:
      default:
        returnLevel = findLevel(messageFragment, DEBUG, INFO, WARN, ERROR);
        break;
    }
    return returnLevel;
  }

}
