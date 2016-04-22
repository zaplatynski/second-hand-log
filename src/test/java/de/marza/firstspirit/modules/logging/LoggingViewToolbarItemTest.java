package de.marza.firstspirit.modules.logging;

import de.marza.firstspirit.modules.logging.toolbar.LoggingViewToolbarItem;

/**
 * Created by Zaplatynski on 14.04.2016.
 */
public class LoggingViewToolbarItemTest {

    private LoggingViewToolbarItem testling;

/*
    @Before
    public void setUp() throws Exception {
        testling = new LoggingViewToolbarItem();
    }

    @Test
    public void execute() throws Exception {
        testling.execute(null);

        System.out.println("Test");

    }
*/

    public static void main(final String[] args) throws Exception {
        final LoggingViewToolbarItem view = new LoggingViewToolbarItem();

        view.execute(null);

        for (int i = 0; i < 100; i++) {
            System.out.println(i + ". test message");
            System.err.println(i + ". error");
            Thread.currentThread().sleep(1000);

        }

    }


}
