package me.wonwoo.flume.exception;

/**
 * Created by wonwoo on 2016. 10. 18..
 */
public class LogBackAppenderException extends RuntimeException {

    private String message;

    public LogBackAppenderException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
