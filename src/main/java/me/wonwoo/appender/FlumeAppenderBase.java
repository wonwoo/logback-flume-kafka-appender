package me.wonwoo.appender;

import ch.qos.logback.core.UnsynchronizedAppenderBase;
import me.wonwoo.encoding.FlumeMessageEncoder;
import me.wonwoo.flume.exception.FlumeInitializeException;
import me.wonwoo.flume.exception.LogBackAppenderException;
import me.wonwoo.flume.sink.FlumeSink;
import org.apache.flume.Event;
import org.apache.flume.event.EventBuilder;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static me.wonwoo.util.Constant.CHARSET;

/**
 * Created by wonwoo on 2016. 6. 7..
 */
public abstract class FlumeAppenderBase<E> extends UnsynchronizedAppenderBase<E> {

  private LazyFlume lazyFlume;
  private FlumeMessageEncoder<E> encoder;

  @Override
  public void start() {
    super.start();
    lazyFlume = new LazyFlume();
  }

  @Override
  protected void append(E e) {
    try {
      final String result = doAppender(e);
      if (result != null) {
        FlumeSink flumeSink = lazyFlume.get();
        Event appender = withBody(result);
        flumeSink.processEvents(Arrays.asList(appender));
      }
    } catch (Exception ex) {
      addError("rpc client error ", ex);
      this.stop();
      throw new LogBackAppenderException("logback appender error ", ex);
    }
  }

  private Event withBody(String data) {
    return EventBuilder.withBody(data, CHARSET);
  }

  /**
   * appender (null 리턴시 sink에서 보내지 않는다.)
   *
   * @param e
   * @return
   */
  protected abstract String doAppender(E e) throws IOException;

  /**
   * sink 생성
   *
   * @return
   */
  protected abstract FlumeSink createSink();

  @Override
  public void stop() {
    super.stop();
    if (lazyFlume != null) {
      lazyFlume.get().shutdown();
    }
    lazyFlume = null;
  }

  /**
   * 라지 로딩 (logback 실행시 락 걸리면 경고창이 떠서 라지 로딩 실시함)
   */
  private class LazyFlume {

    private volatile FlumeSink flumeSink;

    private FlumeSink get() {
      FlumeSink result = this.flumeSink;
      if (result == null) {
        synchronized (this) {
          result = this.flumeSink;
          if (result == null) {
            this.flumeSink = result = this.initialize();
          }
        }
      }
      return result;
    }

    private FlumeSink initialize() {
      FlumeSink flumeSink;
      try {
        flumeSink = createSink();
        flumeSink.start();
      } catch (Exception e) {
        addError("create error ", e);
        throw new FlumeInitializeException("flume sink initialize error  ", e);
      }
      return flumeSink;
    }
  }


  public void setEncoder(FlumeMessageEncoder<E> layout) {
    this.encoder = layout;
  }

  protected FlumeMessageEncoder<E> getEncoder() {
    return encoder;
  }

  private String mode;

  public void setMode(String mode) {
    this.mode = mode;
  }

  protected String getMode() {
    return this.mode;
  }
}
