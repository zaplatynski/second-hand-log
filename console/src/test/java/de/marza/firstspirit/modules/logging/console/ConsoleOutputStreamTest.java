package de.marza.firstspirit.modules.logging.console;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.awt.Color;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;

import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The type Console output stream test.
 */
@RunWith(Parameterized.class)
public class ConsoleOutputStreamTest {

  private static final String EOL = System.lineSeparator();
  private final boolean append;
  /**
   * The Rule.
   */
  @Rule
  public MockitoRule rule = MockitoJUnit.rule();
  private ConsoleOutputStream testling;
  private SimpleAttributeSet attributes;
  @Mock
  private MessageConsole console;

  @Mock
  private Document document;

  @Mock
  private JTextComponent textComponent;

  @Mock
  private PrintStream printStream;

  /**
   * Instantiates a new Console output stream test.
   *
   * @param append the append
   */
  public ConsoleOutputStreamTest(final boolean append) {
    this.append = append;
  }

  /**
   * Data collection.
   *
   * @return the collection
   */
  @Parameterized.Parameters(name = "append={0}")
  public static Collection<Object[]> data() {
    return Arrays.asList(new Boolean[]{Boolean.TRUE}, new Boolean[]{Boolean.FALSE});

  }

  /**
   * Sets up.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUp() throws Exception {
    when(console.isAppend()).thenReturn(append);
    when(console.getTextComponent()).thenReturn(textComponent);
    when(textComponent.getDocument()).thenReturn(document);

    testling = new ConsoleOutputStream(console, Color.BLACK, printStream);

    attributes = new SimpleAttributeSet();
    StyleConstants.setForeground(attributes, Color.BLACK);


  }

  /**
   * Flush.
   *
   * @throws Exception the exception
   */
  @Test
  public void flush() throws Exception {

    if (append) {
      testling.write(("Test 123" + EOL).getBytes());
      testling.flush();
    } else {
      testling.write("Test 123".getBytes());
      testling.flush();

      testling.write(EOL.getBytes());
      testling.flush();

      testling.write("".getBytes());
      testling.flush();
    }

    verify(document).insertString(0, "Test 123" + EOL, attributes);

    verify(printStream).print("Test 123" + EOL);
  }

}