package de.marza.firstspirit.modules.logging.console.utilities;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import javax.swing.text.Element;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RemoveLinesJobTest {

  private RemoveLinesJob testling;

  @Mock
  private DocumentEvent documentEvent;

  @Mock
  private LimitLinesDocumentListener documentListener;

  @Mock
  private Document document;

  @Mock
  private Element rootElement;

  @Mock
  private Element line;

  @Before
  public void setUp() throws Exception {
    testling = new RemoveLinesJob(documentListener, documentEvent);
    when(documentEvent.getDocument()).thenReturn(document);
    when(document.getDefaultRootElement()).thenReturn(rootElement);
  }

  @Test
  public void runDoNothing() throws Exception {
    when(rootElement.getElementCount()).thenReturn(10);
    when(documentListener.getMaximumLines()).thenReturn(100);
    testling.run();
  }

  @Test
  public void runDoRemoveFormStart() throws Exception {
    when(documentListener.isRemoveFromStart()).thenReturn(true);
    when(rootElement.getElementCount()).thenReturn(101).thenReturn(100);
    when(documentListener.getMaximumLines()).thenReturn(100);
    when(rootElement.getElement(0)).thenReturn(line);
    testling.run();
    verify(document).remove(0, 0);
  }

  @Test
  public void runDoRemoveFormEnd() throws Exception {
    when(documentListener.isRemoveFromStart()).thenReturn(false);
    when(rootElement.getElementCount()).thenReturn(101).thenReturn(101).thenReturn(100);
    when(documentListener.getMaximumLines()).thenReturn(100);
    when(rootElement.getElement(100)).thenReturn(line);
    testling.run();
    verify(document).remove(-1, 0);
  }

}