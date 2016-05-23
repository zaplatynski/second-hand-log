package de.marza.firstspirit.modules.logging.console.utilities;

import org.junit.Before;
import org.junit.Test;

import java.awt.GraphicsEnvironment;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeFalse;


/**
 * The type Clipboard access test.
 */
public class ClipboardAccessTest {


  private ClipboardAccess testling;

  /**
   * Sets up.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUp() throws Exception {
    assumeFalse("Run only in desktop", GraphicsEnvironment.isHeadless());
    testling = new ClipboardAccess();
  }

  /**
   * Transfer.
   *
   * @throws Exception the exception
   */
  @Test
  public void transfer() throws Exception {
    testling.copy("Test 123");

    assertThat(testling.paste(), is("Test 123"));
  }

}