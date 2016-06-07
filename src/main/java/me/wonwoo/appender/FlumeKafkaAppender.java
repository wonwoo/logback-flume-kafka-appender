package me.wonwoo.appender;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import me.wonwoo.flume.channel.ChannelAttr;
import me.wonwoo.flume.sink.FlumeSink;
import me.wonwoo.flume.sink.kafka.FlumeKafkaSink;
import me.wonwoo.flume.sink.kafka.KafkaAttr;

import static me.wonwoo.util.Constant.FLUME_PREFIX;

/**
 * Created by wonwoo on 2016. 6. 7..
 */
public class FlumeKafkaAppender extends FlumeAppenderBase<ILoggingEvent> {

  private String sinkName;
  private String channelName;
  private KafkaAttr kafkaAttr;
  private ChannelAttr channelAttr;

  public FlumeKafkaAppender() {
  }

  @Override
  protected FlumeSink createSink() {
    return new FlumeKafkaSink(this.sinkName, this.channelName, this.kafkaAttr, this.channelAttr);
  }

  @Override
  protected String doAppender(final ILoggingEvent event) {
    String loggerName = event.getLoggerName();

    if (loggerName.startsWith(FLUME_PREFIX)) {
      return null;
    }
    int current = event.getLevel().toInt();
    int mode = Level.toLevel(getMode()).toInt();
    if (mode > current) {
      return null;
    }

    return getEncoder().doEncode(event);
  }

  public void setChannelName(String channelName) {
    this.channelName = channelName;
  }

  public void setChannelAttr(ChannelAttr channelAttr) {
    this.channelAttr = channelAttr;
  }

  public void setSinkName(String sinkName) {
    this.sinkName = sinkName;
  }

  public void setKafkaAttr(KafkaAttr kafkaAttr) {
    this.kafkaAttr = kafkaAttr;
  }

}
