package nl.knaw.huygens.tei.xpath;

import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.google.common.collect.ImmutableMap;

public class XPathUtil {
  static XPathFactory xpathFactory = XPathFactory.newInstance();
  static XPath xpath = xpathFactory.newXPath();

  static Map<Class<?>, QName> returnTypes = ImmutableMap.<Class<?>, QName> builder()//
      .put(String.class, XPathConstants.STRING)//
      .put(Boolean.class, XPathConstants.BOOLEAN)//
      .put(Long.class, XPathConstants.NUMBER)//
      .put(Node.class, XPathConstants.NODE)//
      .put(NodeList.class, XPathConstants.NODESET)//
      .build();

  public static <T> T evaluate(String xpathQuery, InputSource source, Class<T> resultClass) throws XPathExpressionException {
    if (returnTypes.containsKey(resultClass)) {
      Object evaluate = xpath.evaluate(xpathQuery, source, returnTypes.get(resultClass));
      return resultClass.cast(evaluate);
    }
    throw new IllegalArgumentException("Can't return a " + resultClass.getName());
  }

  public static String evaluate(String xpathQuery, InputSource source) throws XPathExpressionException {
    return xpath.evaluate(xpathQuery, source);
  }

}
