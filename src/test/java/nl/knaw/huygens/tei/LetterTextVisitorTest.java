package nl.knaw.huygens.tei;

import nl.knaw.huygens.tei.export.LetterTextVisitor;

import org.junit.Assert;
import org.junit.Test;

public class LetterTextVisitorTest {

  private String getBasicTei() {
    StringBuilder builder = new StringBuilder();
    builder.append("<TEI>");
    builder.append("<teiHeader></teiHeader>");
    builder.append("<text><body>");
    builder.append("<div type=\"letter\" id=\"a0028\">");
    builder.append("<head>28. <date>1602 Febr. 3</date>. Van J. Lipsius.</head>");
    builder.append("<p>text.</p>");
    builder.append("</div>");
    builder.append("<div>extra</div>");
    builder.append("</body></text>");
    builder.append("</TEI>");
    return builder.toString();
  }

  private String getMixedLanguageTei() {
    StringBuilder builder = new StringBuilder();
    builder.append("<TEI>");
    builder.append("<text><body>");
    builder.append("<p> text </p>");
    builder.append("<p lang=\"la\"> latin </p>");
    builder.append("<p lang=\"nl\"> dutch </p>");
    builder.append("<p lang=\"la\"> latin </p>");
    builder.append("</body></text>");
    builder.append("</TEI>");
    return builder.toString();
  }

  private String process(String xml, String defaultLanguage, String targetLanguage) {
    Document document = Document.createFromXml(xml);
    LetterTextVisitor visitor = new LetterTextVisitor(defaultLanguage, targetLanguage);
    document.accept(visitor);
    return visitor.getContext().getResult().replaceAll("\\s+", " ").trim();
  }

  private String process(String xml) {
    return process(xml, "?", null);
  }

  @Test
  public void basicTest() {
    Assert.assertEquals("28. 1602 Febr. 3. Van J. Lipsius.text.extra", process(getBasicTei()));
  }

  @Test
  public void testComment() {
    String xml = "<TEI><body>1<div type=\"comment\">2</div></body></TEI>";
    Assert.assertEquals("1", process(xml));
  }

  @Test
  public void testPara() {
    String xml = "<TEI><body>1<div type=\"para\">2</div></body></TEI>";
    Assert.assertEquals("1", process(xml));
  }

  @Test
  public void testSummary() {
    String xml = "<TEI><body>1<div type=\"summary\">2</div></body></TEI>";
    Assert.assertEquals("1", process(xml));
  }

  @Test
  public void testBrackets() {
    String xml = "<TEI><body>Grotius s[alutem dicit].</body></TEI>";
    Assert.assertEquals("Grotius salutem dicit.", process(xml));
  }

  @Test
  public void testParenthesisWithHighlighting() {
    String xml = "<TEI><body><hi rend=\"sc\">Hen</hi>[<hi rend=\"sc\">ricus</hi>] Bruno</body></TEI>";
    Assert.assertEquals("Henricus Bruno", process(xml));
  }

  @Test
  public void testParenthesis() {
    String xml = "<TEI><body>Grotius s(alutem dicit).</body></TEI>";
    Assert.assertEquals("Grotius salutem dicit.", process(xml));
  }

  @Test
  public void testDefaultLanguage() {
    Assert.assertEquals("text latin dutch latin", process(getMixedLanguageTei()));
  }

  @Test
  public void testSelectedLanguage1() {
    Assert.assertEquals("latin latin", process(getMixedLanguageTei(), "?", "la"));
  }

  @Test
  public void testSelectedLanguage2() {
    Assert.assertEquals("text latin latin", process(getMixedLanguageTei(), "la", "la"));
  }

  @Test
  public void testSelectedLanguage3() {
    Assert.assertEquals("dutch", process(getMixedLanguageTei(), "?", "nl"));
  }

  @Test
  public void testSelectedLanguage4() {
    Assert.assertEquals("text dutch", process(getMixedLanguageTei(), "nl", "nl"));
  }

  @Test
  public void testSelectedLanguage5() {
    Assert.assertEquals("dutch", process(getMixedLanguageTei(), "fr", "nl"));
  }

}
