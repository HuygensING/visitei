package nl.knaw.huygens.tei;

/*
 * #%L
 * VisiTEI
 * =======
 * Copyright (C) 2011 - 2017 Huygens ING
 * =======
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

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
