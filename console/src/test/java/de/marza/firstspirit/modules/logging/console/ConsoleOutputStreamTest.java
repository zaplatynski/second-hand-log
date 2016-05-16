package de.marza.firstspirit.modules.logging.console;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.awt.Color;

import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConsoleOutputStreamTest {

  public static final String EOL = System.lineSeparator();

  private ConsoleOutputStream testling;

  private SimpleAttributeSet attributes;

  @Mock
  private MessageConsole console;

  @Mock
  private Document document;

  @Mock
  private JTextComponent textComponent;

  @Before
  public void setUp() throws Exception {
    when(console.isAppend()).thenReturn(true);
    when(console.getTextComponent()).thenReturn(textComponent);
    when(textComponent.getDocument()).thenReturn(document);

    testling = new ConsoleOutputStream(console, Color.BLACK, null);

    attributes = new SimpleAttributeSet();
    StyleConstants.setForeground(attributes, Color.BLACK);
  }

  @Test
  public void flushAppend() throws Exception {
    when(console.isAppend()).thenReturn(true);

    testling.write(("Test 123" + EOL).getBytes());

    testling.flush();

    verify(document).insertString(0, "Test 123" + EOL, attributes);
  }

  @Test
  public void flushInsert() throws Exception {
    when(console.isAppend()).thenReturn(false);

    testling.write("Test 123".getBytes());

    testling.flush();

    testling.write(EOL.getBytes());

    testling.flush();

    verify(document).insertString(0, "Test 123" + EOL, attributes);
  }

}