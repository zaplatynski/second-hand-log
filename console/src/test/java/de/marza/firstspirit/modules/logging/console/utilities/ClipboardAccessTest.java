package de.marza.firstspirit.modules.logging.console.utilities;

import org.junit.Before;
import org.junit.Test;

import java.awt.GraphicsEnvironment;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeFalse;


public class ClipboardAccessTest {


  private ClipboardAccess testling;

  @Before
  public void setUp() throws Exception {
    assumeFalse("Run only in desktop", GraphicsEnvironment.isHeadless());
    testling = new ClipboardAccess();
  }

  @Test
  public void transfer() throws Exception {
    testling.copy("Test 123");

    assertThat(testling.paste(), is("Test 123"));
  }

}