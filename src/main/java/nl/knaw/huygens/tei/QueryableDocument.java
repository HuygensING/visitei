package nl.knaw.huygens.tei;

import java.io.StringReader;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.xml.sax.InputSource;

import net.sf.practicalxml.xpath.NamespaceResolver;
import nl.knaw.huygens.tei.xpath.XPathUtil;

public class QueryableDocument {
  private static final String DEFAULTPREFIX = "ns";
  private String xml;
  private final Document document;
  private Boolean needsPrefix = false;
  private String defaultPrefix = DEFAULTPREFIX;
  private XPath xpath = XPathFactory.newInstance().newXPath();

  private QueryableDocument(Document document) {
    this.document = document;
  }

  public static QueryableDocument createFromXml(String xml, boolean preserveNamespacePrefix) {
    NamespaceResolver namespaceResolver = new NamespaceResolver();
    for (Entry<String, String> entry : XPathUtil.getNamespaceInfo(xml).entrySet()) {
      String prefix = entry.getKey();
      String nsURI = entry.getValue();
      if (!nsURI.equals("") && !prefix.equals("")) {
        namespaceResolver.addNamespace(prefix, nsURI);
      }
    }

    QueryableDocument qDocument = new QueryableDocument(Document.createFromXml(xml, preserveNamespacePrefix));
    qDocument.setXml(removeDefaultNamespace(xml));
    if (!namespaceResolver.getAllPrefixes().isEmpty()) {
      qDocument.setNamespaceContext(namespaceResolver);
    }
    //    List<String> allPrefixes = namespaceResolver.getAllPrefixes();
    //    if (!allPrefixes.isEmpty()) {
    //      qDocument.setNeedsPrefix(true);
    //      String defaultPrefix = calculateDefaultPrefix(allPrefixes);
    //      namespaceResolver.addNamespace(defaultPrefix, namespaceResolver.getNamespaceURI(""));
    //      qDocument.setDefaultPrefix(defaultPrefix);
    //    }
    return qDocument;
  }

  private static String removeDefaultNamespace(String xml) {
    return xml.replaceFirst(" xmlns=\"[^\"]*\"", "");
  }

  //  private static String calculateDefaultPrefix(List<String> allPrefixes) {
  //    String prefix = DEFAULTPREFIX;
  //    int i = 0;
  //    while (allPrefixes.contains(prefix)) {
  //      prefix = DEFAULTPREFIX + i++;
  //    }
  //    return prefix;
  //  }

  private void setNamespaceContext(NamespaceResolver nr) {
    xpath.setNamespaceContext(nr);
  }

  public void setXml(String xml) {
    this.xml = xml;
  }

  public Boolean needsPrefix() {
    return needsPrefix;
  }

  public void setNeedsPrefix(Boolean needsPrefix) {
    this.needsPrefix = needsPrefix;
  }

  public String getDefaultPrefix() {
    return defaultPrefix;
  }

  public void setDefaultPrefix(String defaultPrefix) {
    this.defaultPrefix = defaultPrefix;
  }

  // --- XPath ------------------------------------------------------

  public String evaluateXPathToString(String xpathQuery) throws XPathExpressionException {
    return (String) xpath.evaluate(xpathQuery, inputSource(), XPathConstants.STRING);
  }

  public Long evaluateXPathToLong(String xpathQuery) throws XPathExpressionException {
    return (Long) xpath.evaluate(xpathQuery, inputSource(), XPathConstants.NUMBER);
  }

  public Boolean evaluateXPathToBoolean(String xpathQuery) throws XPathExpressionException {
    return (Boolean) xpath.evaluate(xpathQuery, inputSource(), XPathConstants.BOOLEAN);
  }

  private InputSource inputSource() {
    InputSource source = new InputSource(new StringReader(xml));
    return source;
  }

  // --- delegated methods -------------------------------------

  public int getStartLine() {
    return document.getStartLine();
  }

  public int getStartColumn() {
    return document.getStartColumn();
  }

  public int getEndLine() {
    return document.getEndLine();
  }

  public int getEndColumn() {
    return document.getEndColumn();
  }

  public Element getRoot() {
    return document.getRoot();
  }

  public List<Node> getHeadNodes() {
    return document.getHeadNodes();
  }

  public List<Node> getFootNodes() {
    return document.getFootNodes();
  }

  public void setRoot(Element element) {
    document.setRoot(element);
  }

  public List<Element> getElementsByTagName(String name) {
    return document.getElementsByTagName(name);
  }

  public Traversal accept(Visitor visitor) {
    return document.accept(visitor);
  }

}
