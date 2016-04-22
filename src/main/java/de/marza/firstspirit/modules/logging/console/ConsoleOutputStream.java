package de.marza.firstspirit.modules.logging.console;

import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Class to intercept output from a PrintStream and add it to a Document. The output can optionally be redirected to a different PrintStream. The text
 * displayed in the Document can be color coded to indicate the output source.
 */
class ConsoleOutputStream extends ByteArrayOutputStream {

    private MessageConsole messageConsole;
    private final String EOL = System.lineSeparator();
    private SimpleAttributeSet attributes;
    private PrintStream printStream;
    private StringBuilder buffer = new StringBuilder(80);
    private boolean isFirstLine;

    /**
     * Specify the option text color and PrintStream.
     *
     * @param messageConsole the message console
     * @param textColor      the text color
     * @param printStream    the print stream
     */
    public ConsoleOutputStream(final MessageConsole messageConsole, final Color textColor, final PrintStream printStream) {
        this.messageConsole = messageConsole;
        if (textColor != null) {
            attributes = new SimpleAttributeSet();
            StyleConstants.setForeground(attributes, textColor);
        }

        this.printStream = printStream;

        if (messageConsole.isAppend()) {
            isFirstLine = true;
        }
    }

    /**
     * Override this method to intercept the output text. Each line of text output will actually involve invoking this method twice:
     * <br>
     * a) for the actual text message<br>
     * b) for the newLine string<br>
     * <br>
     * The message will be treated differently depending on whether the line will be appended or inserted into the Document
     */
    public void flush() {
        final String message = toString();

        if (message.length() == 0) {
            return;
        }

        if (messageConsole.isAppend()) {
            handleAppend(message);
        } else {
            handleInsert(message);
        }

        reset();
    }

    /**
     * We don't want to have blank lines in the Document. The first line added will simply be the message. For additional lines it will be:
     *
     * newLine + message
     */
    private void handleAppend(final String message) {
        //  This check is needed in case the text in the Document has been
        //	cleared. The buffer may contain the EOL string from the previous
        //  message.

        if (messageConsole.getDocument().getLength() == 0) {
            buffer.setLength(0);
        }

        if (EOL.equals(message)) {
            buffer.append(message);
        } else {
            buffer.append(message);
            clearBuffer();
        }

    }

    /**
     * We don't want to merge the new message with the existing message so the line will be inserted as:
     *
     * message + newLine
     */
    private void handleInsert(final String message) {
        buffer.append(message);

        if (EOL.equals(message)) {
            clearBuffer();
        }
    }

    /**
     * The message and the newLine have been added to the buffer in the appropriate order so we can now update the Document and send the text to the
     * optional PrintStream.
     */
    private void clearBuffer() {
        //  In case both the standard out and standard err are being redirected
        //  we need to insert a newline character for the first line only

        if (isFirstLine && messageConsole.getDocument().getLength() != 0) {
            buffer.insert(0, "\n");
        }

        isFirstLine = false;
        final String line = buffer.toString();

        try {
            if (messageConsole.isAppend()) {
                final int offset = messageConsole.getDocument().getLength();
                messageConsole.getDocument().insertString(offset, line, attributes);
                messageConsole.getTextComponent().setCaretPosition(messageConsole.getDocument().getLength());
            } else {
                messageConsole.getDocument().insertString(0, line, attributes);
                messageConsole.getTextComponent().setCaretPosition(0);
            }
        } catch (final BadLocationException ble) {
        }

        if (printStream != null) {
            printStream.print(line);
        }

        buffer.setLength(0);
    }
}
