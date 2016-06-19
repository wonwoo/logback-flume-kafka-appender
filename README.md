# logback-flume-kafka-appender

## setting

### kakka 9.0.1.0
[download kafka](http://kafka.apache.org/downloads.html "download kafka")

#### start kafka
1. zookeeper start
```
bin/zookeeper-server-start.sh config/zookeeper.properties
```

2. kafka start
```
bin/kafka-server-start.sh config/server.properties
```

3. consumer start and topic test
```
bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic test
```

### logback.xml
```xml
<appender name="flume" class="me.wonwoo.appender.FlumeKafkaAppender">
    <sinkName>kafkaSink</sinkName>
    <channelName>kafkaChannel</channelName>
    <mode>INFO</mode>
    <!--https://flume.apache.org/FlumeUserGuide.html#file-channel -->
    <channelAttr>
        <capacity>100000</capacity>
        <transactionCapacity>1000</transactionCapacity>
        <dataDirs>/Users/wonwoo/Desktop/flume</dataDirs>
        <checkpointDir>/Users/wonwoo/Desktop/flume/checkout</checkpointDir>
    </channelAttr>
    <!-- topic ,brokerList, batchSize -->
    <KafkaAttr>
        <topic>test</topic>
        <brokerList>localhost:9092</brokerList>
        <!--<brokerList>localhost:9092,localhost:9093,localhost:9094</brokerList>-->
        <batchSize>100</batchSize>
    </KafkaAttr>
    <encoder class="me.wonwoo.encoding.DefaultFlumeMessageEncoder">
        <layout class="ch.qos.logback.classic.PatternLayout">
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg</pattern>
        </layout>
    </encoder>
</appender>
```

1. Default encoding PatternLayout 
```xml
<encoder class="me.wonwoo.encoding.DefaultFlumeMessageEncoder">
    <layout class="ch.qos.logback.classic.PatternLayout">
        <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg</pattern>
    </layout>
</encoder>
```

kakfa consumer 

```
21:17:49.959 [main] ERROR m.w.appender.FlumeKafkaAppenderTest - url formed exception java.net.MalformedURLException: no protocol: asdfsadfas
	at java.net.URL.<init>(URL.java:586)
	at java.net.URL.<init>(URL.java:483)
	at java.net.URL.<init>(URL.java:432)
	at me.wonwoo.appender.FlumeKafkaAppenderTest.appenderKafkaError(FlumeKafkaAppenderTest.java:25)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	
	...
	...
```

2. JsonLayout
```xml
<encoder class="me.wonwoo.encoding.DefaultFlumeMessageEncoder">
    <layout class="me.wonwoo.layout.JsonLayout">
        <jsonFormatter class="me.wonwoo.layout.JacksonJsonFormatter">
            <prettyPrint>true</prettyPrint>
        </jsonFormatter>
        <includeContextName>false</includeContextName>
    </layout>
</encoder>
```
incloude list : 
includeLevel, includeThreadName, includeMDC, includeLoggerName, includeFormattedMessage, includeMessage ,includeException, includeContextName, includeHostName, includeLineNumber

kakfa consumer 

```json
{
  "timestamp" : "1466337714117",
  "level" : "ERROR",
  "thread" : "main",
  "logger" : "me.wonwoo.appender.FlumeKafkaAppenderTest",
  "message" : "url formed exception ",
  "lineNumber" : "29",
  "exception" : "java.net.MalformedURLException: no protocol: asdfsadfas\n\tat java.net.URL.<init>(URL.java:586)\n\tat java.net.URL.<init>(URL.java:483)\n\tat java.net.URL.<init>(URL.java:432)\n\tat me.wonwoo.appender.FlumeKafkaAppenderTest.appenderKafkaError(FlumeKafkaAppenderTest.java:26)\n\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62
  
  ...
  ...
}
```

### TEST

```java
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
```

### maven

```xml
<repositories>
	<repository>
	    <id>spring-boot-gcm-mvn-repo</id>
	    <url>https://raw.github.com/wonwoo/logback-flume-kafka-appender/mvn-repo/</url>
	</repository>
</repositories>


<dependency>
  <groupId>me.wonwoo</groupId>
  <artifactId>logback-flume-kafka-appender</artifactId>
  <version>0.9.0-SNAPSHOT</version>
</dependency>
```




