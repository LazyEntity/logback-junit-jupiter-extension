package junit.jupiter.log.common;

public interface LogRow {
    String getThreadName();

    String getLoggerName();

    String getMessage();

    Level getLevel();

    LogThrowableInfo getThrowableInfo();

    Long getTimeStamp();

    String getMdcValue(String key);

}
