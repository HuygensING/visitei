package nl.knaw.huygens.tei.export;

import nl.knaw.huygens.tei.Document;

public interface DocumentExporter {

  String apply(Document document);

}
