package nl.knaw.huygens.tei;

import java.util.Map;

import org.xml.sax.Attributes;

import com.google.common.collect.Maps;

public class XmlUtils {

  public static Map<String, String> newAttributes() {
    //    return Maps.newTreeMap();
    return Maps.newLinkedHashMap();
  }

  public static Map<String, String> newAttributes(String key, String value) {
    //    Map<String, String> map = Maps.newTreeMap();
    Map<String, String> map = Maps.newLinkedHashMap();
    map.put(key, value);
    return map;
  }

  public static Map<String, String> convertSAXAttributes(Attributes attributes, boolean useQualifiedName) {
    //    Map<String, String> map = Maps.newTreeMap();
    Map<String, String> map = Maps.newLinkedHashMap();
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
