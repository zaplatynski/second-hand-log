package de.marza.firstspirit.modules.logging.fsm;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;


public class FsmIT {

  private static final String MODULE_DESCRIPTOR = "META-INF/module.xml";
  private static Properties pomProperties;

  @Rule
  public ErrorCollector errors = new ErrorCollector();

  @BeforeClass
  public static void setUpBefore() throws Exception {
    pomProperties = new Properties();
    final ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
    final InputStream inputStream = systemClassLoader.getResourceAsStream("moduleTest.properties");
    pomProperties.load(inputStream);
  }

  /**
   * Check if FSM is valid
   */
  @Test
  public void testisFSMValid() throws Exception {
    final File directory = new File("target");
    final Collection<File> files = FileUtils.listFiles(directory, new WildcardFileFilter("*.fsm"), null);
    final Iterator<File> iterator = files.iterator();
    assertTrue("FSM doesn't contain any files", iterator.hasNext());
    if (iterator.hasNext()) {
      try (final ZipFile _fsmZip = new ZipFile(iterator.next())) {
        final ZipEntry license = _fsmZip.getEntry("LICENSE");
        errors.checkThat("Couldn't find module descriptor (module.xml) in fsm file", license,
            is(notNullValue()));
        final ZipEntry moduleXML = _fsmZip.getEntry(MODULE_DESCRIPTOR);
        errors.checkThat("Couldn't find module descriptor (module.xml) in fsm file", moduleXML,
            is(notNullValue()));
        final ZipEntry consoleLib = _fsmZip.getEntry("lib/console-"
            + pomProperties.getProperty("version") + ".jar");
        errors.checkThat("Couldn't find lib in fsm file", consoleLib, is(notNullValue()));
        final ZipEntry toolbarLib = _fsmZip.getEntry("lib/toolbar-"
            + pomProperties.getProperty("version") + ".jar");
        errors.checkThat("Couldn't find lib in fsm file", toolbarLib, is(notNullValue()));
      }
    }

  }

}
