
package me.wonwoo.flume.sink.kafka;

import me.wonwoo.flume.channel.ChannelAttr;
import me.wonwoo.flume.sink.AbstractFlumeSink;
import org.apache.flume.Channel;
import org.apache.flume.Context;
import org.apache.flume.Sink;
import org.apache.flume.channel.file.FileChannel;
import org.apache.flume.conf.Configurables;
import org.apache.flume.sink.kafka.KafkaSink;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.Map;
import java.util.UUID;

/**
 * Created by wonwoo on 2016. 6. 7..
 */
public class FlumeKafkaSink extends AbstractFlumeSink {

  private final String sinkName;
  private final String channelName;
  private final KafkaAttr kafkaAttr;
  private final ChannelAttr channelAttr;
  private final ObjectMapper objectMapper;

  public FlumeKafkaSink(String sinkName, String channelName, KafkaAttr kafkaAttr, ChannelAttr channelAttr) {
    this.sinkName = sinkName;
    this.channelName = channelName;
    this.kafkaAttr = kafkaAttr;
    this.channelAttr = channelAttr;
    this.objectMapper = new ObjectMapper();
  }

  @Override
  protected Channel createChannel() {
    Channel channel = new FileChannel();
    channel.setName(this.channelName + "-" + UUID.randomUUID());
    //채널 설정
    Map<String, String> channelParameters = objectMapper.convertValue(this.channelAttr, Map.class);
    Context channelContext = new Context(channelParameters);

    Configurables.configure(channel, channelContext);
    return channel;
  }

  @Override
  protected Sink createSink() {
    Sink sink = new KafkaSink();
    sink.setName(this.sinkName + "-" + UUID.randomUUID());
    //카프카 설정
    Map<String, String> sinkParameters = objectMapper.convertValue(this.kafkaAttr, Map.class);
    Context sinkContext = new Context(sinkParameters);
    Configurables.configure(sink, sinkContext);
    return sink;
  }
}
