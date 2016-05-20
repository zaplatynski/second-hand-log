package de.marza.firstspirit.modules.logging.console.utilities;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class LimitLinesDocumentListenerTest {

  private LimitLinesDocumentListener testling;

  @Before
  public void before() throws Exception {
    testling = new LimitLinesDocumentListener(10, true);
  }


  /**
   * Method: setMaxiumLines(final int maximumLines)
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSetMaxiumLines() throws Exception {
    testling = new LimitLinesDocumentListener(0, true);
  }

  /**
   * Method: isRemoveFromStart()
   */
  @Test
  public void testIsRemoveFromStart() throws Exception {
    assertThat(testling.isRemoveFromStart(), is(true));

    testling = new LimitLinesDocumentListener(10, false);

    assertThat(testling.isRemoveFromStart(), is(false));
  }

  @Test
  public void testGetMaximumLines() throws Exception {
    assertThat(testling.getMaximumLines(), is(10));
  }
}
