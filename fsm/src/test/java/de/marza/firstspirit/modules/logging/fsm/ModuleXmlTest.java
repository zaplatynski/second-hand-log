
package de.marza.firstspirit.modules.logging.fsm;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;


/**
 * The type Module xml test.
 */
public class ModuleXmlTest {

  private static Node moduleXML;
  private static Properties pomProperties;

  /**
   * The Errors.
   */
  @Rule
  public ErrorCollector errors = new ErrorCollector();

  /**
   * Sets up before.
   *
   * @throws Exception the exception
   */
  @BeforeClass
  public static void setUpBefore() throws Exception {
    final File file = new File("target/module.xml");
    final String content = FileUtils.readFileToString(file);
    moduleXML = createXMLfromString(content);

    pomProperties = new Properties();
    pomProperties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("moduleTest.properties"));
  }

  private static Node createXMLfromString(final String xmlString) throws Exception {
    return DocumentBuilderFactory
        .newInstance()
        .newDocumentBuilder()
        .parse(new ByteArrayInputStream(xmlString.getBytes()))
        .getDocumentElement();
  }

  /**
   * Test if version is equal to pom version.
   *
   * @throws Exception the exception
   */
  @Test
  public void testIfVersionIsEqualToPomVersion() throws Exception {
    final String expectedVersion = pomProperties.getProperty("version");
    assertThat("Expect a specific value", moduleXML, hasXPath("/module/version", equalTo(expectedVersion)));
  }


  /**
   * Test if display name is equal to pom name.
   *
   * @throws Exception the exception
   */
  @Test
  public void testIfDisplayNameIsEqualToPomName() throws Exception {
    final String expectedName = pomProperties.getProperty("displayName");
    assertThat("Expect a specific value", moduleXML, hasXPath("/module/displayname", equalTo(expectedName)));
  }

  /**
   * Test if name is equal tobasicworkflows.
   *
   * @throws Exception the exception
   */
  @Test
  public void testIfNameIsEqualToModuleName() throws Exception {
    final String expectedName = pomProperties.getProperty("name");
    assertThat("Expect a specific value", moduleXML, hasXPath("/module/name", equalTo(expectedName)));
  }

  /**
   * Test if vendor is equal to pom dev.
   *
   * @throws Exception the exception
   */
  @Test
  public void testIfVendorIsEqualToPomDev() throws Exception {
    final String expectedVendor = pomProperties.getProperty("vendor");
    assertThat("Expect a specific path", moduleXML, hasXPath("/module/vendor", equalTo(expectedVendor)));
  }

  /**
   * Test if description is equal to artifact id.
   *
   * @throws Exception the exception
   */
  @Test
  public void testIfDescriptionIsEqualToArtifactId() throws Exception {
    final String expectedDescription = pomProperties.getProperty("description");
    assertThat("Expect a specific value", moduleXML, hasXPath("/module/description", equalTo(expectedDescription)));
  }

  /**
   * Test all classes.
   *
   * @throws Exception the exception
   */
  @Test
  public void testAllClasses() throws Exception {
    final XPath xPath = XPathFactory.newInstance().newXPath();
    final NodeList nodes = (NodeList) xPath.evaluate("//class", moduleXML, XPathConstants.NODESET);
    assumeThat(nodes, is(notNullValue()));
    System.out.println("Number of classes: " + nodes.getLength());
    for (int i = 0; i < nodes.getLength(); ++i) {
      final Node clazz = nodes.item(i);
      System.out.println("Check if '" + clazz.getTextContent() + "' is existent ...");
      try {
        Class.forName(clazz.getTextContent());
      } catch (final Exception e) {
        errors.addError(e);
      }
    }
  }
}
