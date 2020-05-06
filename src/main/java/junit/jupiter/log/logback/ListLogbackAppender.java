package junit.jupiter.log.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import junit.jupiter.log.common.ListLogAppender;

public interface ListLogbackAppender extends ListLogAppender, Appender<ILoggingEvent> {
}
