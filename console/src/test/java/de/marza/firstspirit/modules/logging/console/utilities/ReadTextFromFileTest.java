package de.marza.firstspirit.modules.logging.console.utilities;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemErrRule;

import javax.swing.JEditorPane;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * The type Read text from file test.
 */
public class ReadTextFromFileTest {

  /**
   * The constant EOL.
   */
  public static final String EOL = System.lineSeparator();
  /**
   * The Rule.
   */
  @Rule
  public SystemErrRule rule = new SystemErrRule();

  private ReadTextFromFile testling;

  /**
   * Sets up.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUp() throws Exception {
    testling = new ReadTextFromFile("/test.txt");
  }

  /**
   * Read as j editor.
   *
   * @throws Exception the exception
   */
  @Test
  public void readAsJEditor() throws Exception {
    final JEditorPane editorPane = testling.readAsJEditor();

    final String expectedText = "<html>\n  " +
        "<head>\n    \n  </head>\n  " +
        "<body>\n    This is a test!\n  </body>\n" +
        "</html>\n";
    assertThat("Expect specific value", editorPane.getText(), is(expectedText));
  }

  /**
   * Test null argument constructor.
   *
   * @throws Exception the exception
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullArgumentConstructor() throws Exception {
    new ReadTextFromFile(null);
  }

  /**
   * Test file not found.
   *
   * @throws Exception the exception
   */
  @Test
  @Ignore
  public void testFileNotFound() throws Exception {

    rule.enableLog();

    testling = new ReadTextFromFile("/test.text");

    testling.readAsJEditor();

    assertThat("Expect specific value", "An logError occurred while reading the about text: java.lang.NullPointerException" + EOL,
        is(rule.getLog()));
  }
}