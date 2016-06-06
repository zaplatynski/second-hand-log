package de.marza.firstspirit.modules.logging.console.utilities;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LevelTest {
  private Level tesling;

  @Before
  public void setUp() throws Exception {
    tesling = Level.DEFAULT;
  }

  @Test
  public void refineByNextFragementError() throws Exception {
    final Level result = tesling.valueOfFragment("DEBUG INFO WARN ERROR This is a message...");

    assertThat(result, is(Level.ERROR));
  }

  @Test
  public void refineByNextFragementWarning() throws Exception {
    final Level result = tesling.valueOfFragment("DEBUG INFO WARN This is a message...");

    assertThat(result, is(Level.WARN));
  }

  @Test
  public void refineByNextFragementInfo() throws Exception {
    final Level result = tesling.valueOfFragment("DEBUG INFO This is a message...");

    assertThat(result, is(Level.INFO));
  }

  @Test
  public void refineByNextFragementDebug() throws Exception {
    final Level result = tesling.valueOfFragment("DEBUG This is a message...");

    assertThat(result, is(Level.DEBUG));
  }

  @Test
  public void refineByNextFragement() throws Exception {
    final Level result = tesling.valueOfFragment("This is a message...");

    assertThat(result, is(Level.DEFAULT));
  }

  @Test
  public void refineByNextFragementNull() throws Exception {
    final Level result = Level.WARN.valueOfFragment(null);

    assertThat(result, is(Level.WARN));
  }

}