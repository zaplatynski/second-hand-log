package de.marza.firstspirit.modules.logging.console.utilities;

import org.junit.Before;
import org.junit.Test;

import java.awt.datatransfer.ClipboardOwner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

/**
 * The type Log clipboard onwer test.
 */
public class LogClipboardOnwerTest {

  private ClipboardOwner testling;

  /**
   * Sets up.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUp() throws Exception {
    testling = LogClipboardOnwer.getInstance();
  }

  /**
   * Gets instance.
   *
   * @throws Exception the exception
   */
  @Test
  public void getInstance() throws Exception {
    assertThat(testling, is(sameInstance(LogClipboardOnwer.getInstance())));
  }

  /**
   * Lost ownership.
   *
   * @throws Exception the exception
   */
  @Test
  public void lostOwnership() throws Exception {
    testling.lostOwnership(null, null);
  }

}