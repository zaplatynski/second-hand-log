package de.marza.firstspirit.modules.logging.console.utilities;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


/**
 * The type Limit lines document listener test.
 */
public class LimitLinesDocumentListenerTest {

  private LimitLinesDocumentListener testling;

  /**
   * Before.
   *
   * @throws Exception the exception
   */
  @Before
  public void before() throws Exception {
    testling = new LimitLinesDocumentListener(10, true);
  }


  /**
   * Method: setMaxiumLines(final int maximumLines).
   *
   * @throws Exception the exception
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSetMaxiumLines() throws Exception {
    testling = new LimitLinesDocumentListener(0, true);
  }

  /**
   * Method: isRemoveFromStart().
   *
   * @throws Exception the exception
   */
  @Test
  public void testIsRemoveFromStart() throws Exception {
    assertThat("Expect specific value", testling.isRemoveFromStart(), is(true));

    testling = new LimitLinesDocumentListener(10, false);

    assertThat("Expect specific value", testling.isRemoveFromStart(), is(false));
  }

  /**
   * Test get maximum lines.
   *
   * @throws Exception the exception
   */
  @Test
  public void testGetMaximumLines() throws Exception {
    assertThat("Expect specific value", testling.getMaximumLines(), is(10));
  }
}
