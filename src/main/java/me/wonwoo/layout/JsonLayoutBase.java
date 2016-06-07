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

import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.LayoutBase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author wonwoo
 */
public abstract class JsonLayoutBase<E> extends LayoutBase<E> {

  public final static String CONTENT_TYPE = "application/json";

  protected boolean includeTimestamp;
  protected String timestampFormat;
  protected String timestampFormatTimezoneId;
  protected boolean appendLineSeparator;

  protected JsonFormatter jsonFormatter;

  public JsonLayoutBase() {
    this.includeTimestamp = true;
    this.appendLineSeparator = false;
  }

  @Override
  public String doLayout(E event) {
    Map<String, Object> map = toJsonMap(event);
    if (map == null || map.isEmpty()) {
      return null;
    }
    String result = getStringFromFormatter(map);
    return isAppendLineSeparator() ? result + CoreConstants.LINE_SEPARATOR : result;
  }

  private String getStringFromFormatter(Map<String, Object> map) {
    JsonFormatter formatter = getJsonFormatter();
    if (formatter == null) {
      addError("JsonFormatter has not been configured on JsonLayout instance " + getClass().getName() + ".  Defaulting to map.toString().");
      return map.toString();
    }

    try {
      return formatter.toJsonString(map);
    } catch (Exception e) {
      addError("JsonFormatter failed.  Defaulting to map.toString().  Message: " + e.getMessage(), e);
      return map.toString();
    }
  }

  protected String formatTimestamp(long timestamp) {
    if (this.timestampFormat == null || timestamp < 0) {
      return String.valueOf(timestamp);
    }
    Date date = new Date(timestamp);
    DateFormat format = createDateFormat(this.timestampFormat);

    if (this.timestampFormatTimezoneId != null) {
      TimeZone tz = TimeZone.getTimeZone(this.timestampFormatTimezoneId);
      format.setTimeZone(tz);
    }

    return format(date, format);
  }

  protected DateFormat createDateFormat(String timestampFormat) {
    return new SimpleDateFormat(timestampFormat);
  }

  protected String format(Date date, DateFormat format) {
    return format.format(date);
  }

  protected abstract Map<String, Object> toJsonMap(E e);

  @Override
  public String getContentType() {
    return CONTENT_TYPE;
  }

  public boolean isIncludeTimestamp() {
    return includeTimestamp;
  }

  public void setIncludeTimestamp(boolean includeTimestamp) {
    this.includeTimestamp = includeTimestamp;
  }

  public JsonFormatter getJsonFormatter() {
    return jsonFormatter;
  }

  public void setJsonFormatter(JsonFormatter jsonFormatter) {
    this.jsonFormatter = jsonFormatter;
  }

  public String getTimestampFormat() {
    return timestampFormat;
  }

  public void setTimestampFormat(String timestampFormat) {
    this.timestampFormat = timestampFormat;
  }

  public String getTimestampFormatTimezoneId() {
    return timestampFormatTimezoneId;
  }

  public void setTimestampFormatTimezoneId(String timestampFormatTimezoneId) {
    this.timestampFormatTimezoneId = timestampFormatTimezoneId;
  }

  public boolean isAppendLineSeparator() {
    return false;
  }

  public void setAppendLineSeparator(boolean appendLineSeparator) {
    this.appendLineSeparator = appendLineSeparator;
  }
}