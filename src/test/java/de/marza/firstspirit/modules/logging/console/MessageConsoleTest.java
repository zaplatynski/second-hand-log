package de.marza.firstspirit.modules.logging.console;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.awt.Color;

import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MessageConsoleTest {

  private MessageConsole testling;

  @Mock
  private JTextComponent textComponent;

  @Mock
  private Document document;

  @Before
  public void setUp() throws Exception {

    when(textComponent.getDocument()).thenReturn(document);

    testling = new MessageConsole(textComponent);

    verify(textComponent).setEditable(false);
    verify(textComponent).setBackground(Color.WHITE);
  }

  @Test
  public void getDocument() throws Exception {
    assertThat(testling.getDocument(), is(sameInstance(document)));
  }

  @Test
  public void isAppend() throws Exception {
    assertThat(testling.isAppend(), is(true));
  }

  @Test
  public void getTextComponent() throws Exception {
    assertThat(testling.getTextComponent(), is(sameInstance(textComponent)));
  }

}