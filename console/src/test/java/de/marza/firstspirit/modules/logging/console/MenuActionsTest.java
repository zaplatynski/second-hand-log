package de.marza.firstspirit.modules.logging.console;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * The type Menu actions test.
 */
@RunWith(Theories.class)
public class MenuActionsTest {

  /**
   * The Testcases.
   */
  @DataPoints
  public static Object[][] testcases = {{MenuActions.SHOW_LOG_LINES_100, 100},
      {MenuActions.SHOW_LOG_LINES_500, 500},
      {MenuActions.SHOW_LOG_LINES_1K, 1000},
      {MenuActions.SHOW_LOG_LINES_2K, 2000},
      {MenuActions.SHOW_LOG_LINES_5K, 5000},
      {MenuActions.SHOW_LOG_LINES_10K, 10000}};


  /**
   * Gets lines.
   *
   * @param testcase the testcase
   * @throws Exception the exception
   */
  @Theory
  public void getLines(final Object[] testcase) throws Exception {
    final MenuActions menuAction = (MenuActions) testcase[0];
    final Integer lines = (Integer) testcase[1];

    assertThat(menuAction.getLines(), is(lines));
  }

  /**
   * Log lines.
   *
   * @throws Exception the exception
   */
  @Test
  public void logLines() throws Exception {
    assertThat(MenuActions.logLines(),
        contains(MenuActions.SHOW_LOG_LINES_100,
            MenuActions.SHOW_LOG_LINES_500,
            MenuActions.SHOW_LOG_LINES_1K,
            MenuActions.SHOW_LOG_LINES_2K,
            MenuActions.SHOW_LOG_LINES_5K,
            MenuActions.SHOW_LOG_LINES_10K));
  }

}