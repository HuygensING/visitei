package nl.knaw.huygens.tei.export;

import java.io.File;

import nl.knaw.huygens.tei.Document;

import org.junit.Assert;
import org.junit.Test;

public class ExportVisitorTest {

	private String process(String xml) {
		Document document = Document.createFromXml(xml);
		ExportVisitor visitor = new ExportVisitor();
		document.accept(visitor);
		return visitor.getContext().getResult();
	}

	@Test
	public void testBasicXml() {
		String xml = "<TEI><text><head><h1>Hello World</h1></head></text></TEI>";
		Assert.assertEquals(xml, process(xml));
	}

	@Test
	public void testEntities() {
		String xml = "<TEI><text>ge&lt;daen&gt;, &amp;c.</text></TEI>";
		Assert.assertEquals(xml, process(xml));
	}

	@Test
	public void testComplexXml() {
		// File has UNIX style EOL's.
		File file = new File("data/test/grotius/a0001.xml");
		String xml = Files.readTextFromFile(file).trim();
		// Normalize order of attributes
		String normalized = process(xml);
		Assert.assertEquals(xml.length(), normalized.length());
		// Normalized xml should be idempotent
		Assert.assertEquals(normalized, process(normalized));
	}

}
