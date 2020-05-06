package junit.jupiter.log.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import junit.jupiter.log.common.Level;
import junit.jupiter.log.common.LogRow;
import junit.jupiter.log.common.LogThrowableInfo;

import java.util.Optional;
import java.util.function.Function;

public final class LogbackEventToLogbackRowMapper implements Function<ILoggingEvent, LogRow> {
    @Override
    public LogRow apply(ILoggingEvent event) {
        return LogbackRow.builder()
                .threadName(event.getThreadName())
                .loggerName(event.getLoggerName())
                .message(event.getFormattedMessage())
                .level(getLevel(event))
                .throwableInfo(getThrowableInfo(event))
                .timeStamp(event.getTimeStamp())
                .mdc(event.getMDCPropertyMap())
                .build();
    }

    private Level getLevel(ILoggingEvent event) {
        return Optional.ofNullable(event.getLevel())
                .map(level -> level.levelStr)
                .map(Level::valueOf)
                .orElse(null);
    }

    private LogThrowableInfo getThrowableInfo(ILoggingEvent event) {
        return Optional.ofNullable(event.getThrowableProxy())
                .map(this::convertThrowableProxyToLogThrowableInfo)
                .orElse(null);
    }

    private LogThrowableInfo convertThrowableProxyToLogThrowableInfo(IThrowableProxy throwableProxy) {
        return LogbackThrowableInfo.builder()
                .message(throwableProxy.getMessage())
                .className(throwableProxy.getClassName())
                .build();
    }
}
