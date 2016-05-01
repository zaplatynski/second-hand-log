package de.marza.firstspirit.modules.logging.console.utilities;

import org.junit.Before;
import org.junit.Test;

import java.awt.datatransfer.ClipboardOwner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

public class LogClipboardOnwerTest {

  private ClipboardOwner testling;

  @Before
  public void setUp() throws Exception {
    testling = LogClipboardOnwer.getInstance();
  }

  @Test
  public void getInstance() throws Exception {
    assertThat(testling, is(sameInstance(LogClipboardOnwer.getInstance())));
  }

  @Test
  public void lostOwnership() throws Exception {
    testling.lostOwnership(null, null);
  }

}