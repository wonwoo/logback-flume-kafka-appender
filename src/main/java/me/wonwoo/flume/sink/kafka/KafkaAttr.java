package me.wonwoo.flume.sink.kafka;

import lombok.Data;

/**
 * Created by wonwoo on 2016. 6. 7..
 */
@Data
public class KafkaAttr {
  private String brokerList;
  private String topic;
  private String batchSize;
  private String requiredAcks;
}
