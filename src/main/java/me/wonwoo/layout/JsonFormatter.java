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

import java.util.Map;

public interface JsonFormatter {

  String toJsonString(Map<String, Object> m) throws Exception;
}