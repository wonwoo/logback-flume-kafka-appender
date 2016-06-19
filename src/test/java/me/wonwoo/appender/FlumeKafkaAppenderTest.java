package me.wonwoo.appender;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wonwoo on 2016. 6. 7..
 */
public class FlumeKafkaAppenderTest {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Test
  public void appenderKafkaTest() {
    logger.info("kafka1 sink test");
  }

  @Test
  public void appenderKafkaError() {
    try {
      URL uri = new URL("urltest");
      uri.openConnection();
    } catch (MalformedURLException e) {
      logger.error("url formed exception ", e);
    } catch (IOException e) {
      logger.error("error {} : ", e.toString());
    }
  }
}