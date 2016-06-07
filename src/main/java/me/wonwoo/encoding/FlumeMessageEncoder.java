package me.wonwoo.encoding;

/**
 * Created by wonwoo on 2016. 6. 7..
 */
public interface FlumeMessageEncoder<E> {

  String doEncode(E event);
}
