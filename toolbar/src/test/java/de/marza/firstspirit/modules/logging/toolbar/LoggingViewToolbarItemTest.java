package de.marza.firstspirit.modules.logging.toolbar;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;


/**
 * The type Logging view toolbar item test.
 */
public class LoggingViewToolbarItemTest {

  private LoggingViewToolbarItem testling;

  /**
   * Sets up.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUp() throws Exception {
    testling = new LoggingViewToolbarItem();
  }

  /**
   * Gets label.
   *
   * @throws Exception the exception
   */
  @Test
  public void getLabel() throws Exception {
    assertThat(testling.getLabel(null), not(isEmptyString()));
  }

  /**
   * Is enabled.
   *
   * @throws Exception the exception
   */
  @Test
  public void isEnabled() throws Exception {
    assertThat(testling.isEnabled(null), is(true));
  }

  /**
   * Is visible.
   *
   * @throws Exception the exception
   */
  @Test
  public void isVisible() throws Exception {
    assertThat(testling.isVisible(null), is(true));
  }

  /**
   * Gets icon.
   *
   * @throws Exception the exception
   */
  @Test
  public void getIcon() throws Exception {
    assertThat(testling.getIcon(null), is(notNullValue()));
  }

  /**
   * Gets pressed icon.
   *
   * @throws Exception the exception
   */
  @Test
  public void getPressedIcon() throws Exception {
    assertThat(testling.getPressedIcon(null), is(notNullValue()));
  }

  /**
   * Gets roll over icon.
   *
   * @throws Exception the exception
   */
  @Test
  public void getRollOverIcon() throws Exception {
    assertThat(testling.getRollOverIcon(null), is(notNullValue()));
  }
}