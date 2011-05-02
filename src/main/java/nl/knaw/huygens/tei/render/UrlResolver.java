package nl.knaw.huygens.tei.render;

/**
 * Resolves a key to a URL.
 */
public interface UrlResolver {

  String resolve(String key);

}
