package me.wonwoo.flume.channel;

import lombok.Data;

/**
 * Created by wonwoo on 2016. 6. 7..
 * <p>
 * https://flume.apache.org/FlumeUserGuide.html#file-channel
 */
@Data
public class ChannelAttr {

  private String capacity;
  private String keepAlive;
  private String transactionCapacity;
  private String checkpointInterval;
  private String maxFileSize;
  private String minimumRequiredSpace;
  private String checkpointDir;
  private String backupCheckpointDir;
  private String dataDirs;

  private String useLogReplayV1;
  private String useFastReplay;
  private String encryptionActiveKey;
  private String encryptionCipherProvider;
  private String useDualCheckpoints;
  private String compressBackupCheckpoint;
  private String fsyncPerTransaction;
  private String fsyncInterval;
  private String checkpointOnClose;
}
