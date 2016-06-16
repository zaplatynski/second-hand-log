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
      if (isLevel(level, messageFragment)) {
        return level.valueOfFragment(false, messageFragment); //NOPMD
      }
    }
    return DEFAULT;
  }

  private static boolean isLevel(final Level level, final String messageFragment) {
    final String lowerCaseLevel = level.nameToLowerCase();
    return messageFragment.startsWith(lowerCaseLevel) || messageFragment.contains(lowerCaseLevel);
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
    return valueOfFragment(true, messageFragment);
  }

  private Level valueOfFragment(final boolean doTrimAndLoweCase, final String messageFragment) {
    if (isEmpty(messageFragment)) {
      return this;
    }
    final String lowerCaseFragment;
    if (doTrimAndLoweCase) {
      final String cleaned = messageFragment.trim();
      final Locale locale = Locale.getDefault();
      lowerCaseFragment = cleaned.toLowerCase(locale); //NOPMD
    } else {
      lowerCaseFragment = messageFragment;
    }
    Level returnLevel;
    switch (this) {
      case DEBUG:
        returnLevel = findLevel(lowerCaseFragment, INFO, WARN, ERROR);
        returnLevel = figureOut(returnLevel);
        break;
      case INFO:
        returnLevel = findLevel(lowerCaseFragment, WARN, ERROR);
        returnLevel = figureOut(returnLevel);
        break;
      case WARN:
        if (isLevel(ERROR, lowerCaseFragment)) {
          returnLevel = ERROR;
        } else {
          returnLevel = this;
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

  private Level figureOut(final Level level) {
    final Level returnLevel;
    if (level == DEFAULT) {
      returnLevel = this;
    } else {
      returnLevel = level;
    }
    return returnLevel;
  }

}
