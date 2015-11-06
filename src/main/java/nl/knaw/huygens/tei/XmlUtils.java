package nl.knaw.huygens.tei;

import java.util.Map;
import org.xml.sax.Attributes;
import com.google.common.collect.Maps;

public class XmlUtils {

  public static Map<String, String> newAttributes() {
    // use LinkedHashMap to preserve attribute order
    return Maps.newLinkedHashMap();
  }

  public static Map<String, String> newAttributes(String key, String value) {
    Map<String, String> map = newAttributes();
    map.put(key, value);
    return map;
  }

  public static Map<String, String> convertSAXAttributes(Attributes attributes, boolean useQualifiedName) {
    Map<String, String> map = newAttributes();
    for (int i = 0; i < attributes.getLength(); i++) {
      String key = useQualifiedName ? attributes.getQName(i) : attributes.getLocalName(i);
      String value = attributes.getValue(i);
      map.put(key, value);
    }
    return map;
  }

  private XmlUtils() {
    throw new AssertionError("Non-instantiable class");
  }

}
