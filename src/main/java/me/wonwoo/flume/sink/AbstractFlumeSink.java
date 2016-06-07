package me.wonwoo.flume.sink;

import org.apache.flume.Channel;
import org.apache.flume.Event;
import org.apache.flume.Sink;
import org.apache.flume.Transaction;

import java.util.List;

import static me.wonwoo.util.AssertUtils.assertNotNull;

/**
 * Created by wonwoo on 2016. 6. 7..
 */
public abstract class AbstractFlumeSink implements FlumeSink {

  private Channel channel;
  private Sink sink;

  @Override
  public void processEvents(List<Event> events) throws Exception {
    Transaction txn = channel.getTransaction();
    txn.begin();
    for (Event event : events) {
      channel.put(event);
    }
    txn.commit();
    txn.close();
    sink.process();
  }

  @Override
  public void shutdown() {
    if (channel != null) {
      channel.stop();
    }
    if (sink != null) {
      sink.stop();
    }
  }


  @Override
  public void start() {
    sink = createSink();
    channel = createChannel();
    assertNotNull(sink, "sink");
    assertNotNull(channel, "channel");

    sink.setChannel(channel);
    sink.start();
    channel.start();
  }

  protected abstract Channel createChannel();

  protected abstract Sink createSink();
}
