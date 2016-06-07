/**
 * Copyright (C) 2016, The logback-contrib developers. All rights reserved.
 * <p>
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 * <p>
 * or (per the licensee's choosing)
 * <p>
 * under the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation.
 */
package me.wonwoo.layout;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * @author wonwoo
 */
public class JacksonJsonFormatter implements JsonFormatter {

  private ObjectMapper objectMapper;
  private boolean prettyPrint;

  public JacksonJsonFormatter() {
    this.objectMapper = new ObjectMapper();
    this.prettyPrint = false;
  }

  @Override
  public String toJsonString(Map<String, Object> map) throws IOException {
    if (prettyPrint) {
      return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
    }
    return objectMapper.writeValueAsString(map);
  }

  public void setPrettyPrint(boolean prettyPrint) {
    this.prettyPrint = prettyPrint;
  }
}