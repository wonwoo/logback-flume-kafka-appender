package me.wonwoo.flume.exception;

/**
 * Created by wonwoo on 2016. 10. 18..
 */
public class FlumeInitializeException extends RuntimeException {
    private String message;

    public FlumeInitializeException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
