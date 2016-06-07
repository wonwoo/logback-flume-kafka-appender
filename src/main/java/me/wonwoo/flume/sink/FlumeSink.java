package me.wonwoo.flume.sink;

import org.apache.flume.Event;

import java.util.List;

/**
 * Created by wonwoo on 2016. 6. 7..
 */
public interface FlumeSink {

  void processEvents(List<Event> events) throws Exception;

  void start();

  void shutdown();
}
