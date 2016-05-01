package de.marza.firstspirit.modules.logging.console.utilities;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class ClipboardAccessTest {


  private ClipboardAccess testling;

  @Before
  public void setUp() throws Exception {
    testling = new ClipboardAccess();
  }

  @Test
  public void transfer() throws Exception {
    testling.copy("Test 123");

    assertThat(testling.paste(), is("Test 123"));
  }

}