package nl.knaw.huygens.tei.export;

import nl.knaw.huygens.tei.Document;

public class DefaultDocumentExporter implements DocumentExporter {

  @Override
  public String apply(Document document) {
    ExportVisitor exporter = new ExportVisitor();
    document.accept(exporter);
    return exporter.getResult();
  }

}
