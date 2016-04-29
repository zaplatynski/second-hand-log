package de.marza.firstspirit.modules.logging.toolbar;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;


public class LoggingViewToolbarItemTest {

  private LoggingViewToolbarItem testling;

  @Before
  public void setUp() throws Exception {
    testling = new LoggingViewToolbarItem();
  }

  @Test
  public void getLabel() throws Exception {
    assertThat(testling.getLabel(null), not(isEmptyString()));
  }

  @Test
  public void isEnabled() throws Exception {
    assertThat(testling.isEnabled(null), is(true));
  }

  @Test
  public void isVisible() throws Exception {
    assertThat(testling.isVisible(null), is(true));
  }

  @Test
  public void getIcon() throws Exception {
    assertThat(testling.getIcon(null), is(notNullValue()));
  }

  @Test
  public void getPressedIcon() throws Exception {
    assertThat(testling.getPressedIcon(null), is(notNullValue()));
  }

  @Test
  public void getRollOverIcon() throws Exception {
    assertThat(testling.getRollOverIcon(null), is(notNullValue()));
  }
}