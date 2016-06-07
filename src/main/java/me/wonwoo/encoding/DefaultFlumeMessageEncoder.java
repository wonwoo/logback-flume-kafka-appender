package me.wonwoo.encoding;

import ch.qos.logback.core.Layout;

/**
 * Created by wonwoo on 2016. 6. 7..
 */
public class DefaultFlumeMessageEncoder<E> implements FlumeMessageEncoder<E> {

  private Layout<E> layout;

  @Override
  public String doEncode(E event) {
    return layout.doLayout(event);
  }

  public void setLayout(Layout<E> layout) {
    this.layout = layout;
  }

  public Layout<E> getLayout() {
    return layout;
  }

}
