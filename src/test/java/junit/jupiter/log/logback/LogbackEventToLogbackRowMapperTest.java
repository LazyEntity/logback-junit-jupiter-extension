package junit.jupiter.log.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import junit.jupiter.log.common.LogRow;
import org.junit.jupiter.api.Test;

import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LogbackEventToLogbackRowMapperTest {
    private final LogbackEventToLogbackRowMapper mapper = new LogbackEventToLogbackRowMapper();

    @Test
    void values_filled_mapping() {
        ILoggingEvent event = mock(ILoggingEvent.class);
        when(event.getThreadName()).thenReturn("main-thread");
        when(event.getLoggerName()).thenReturn("testLogger");
        when(event.getFormattedMessage()).thenReturn("Test message");
        when(event.getLevel()).thenReturn(Level.ERROR);
        when(event.getThrowableProxy()).thenReturn(new ThrowableProxy(new RuntimeException("Test error message")));
        when(event.getTimeStamp()).thenReturn(-2546575200L);
        when(event.getMDCPropertyMap()).thenReturn(emptyMap());

        // call
        LogRow logRow = mapper.apply(event);

        LogbackRow logbackRow = (LogbackRow) logRow;
        assertThat(logbackRow.getThreadName()).isEqualTo("main-thread");
        assertThat(logbackRow.getLoggerName()).isEqualTo("testLogger");
        assertThat(logbackRow.getMessage()).isEqualTo("Test message");
        assertThat(logbackRow.getLevel()).isEqualTo(junit.jupiter.log.common.Level.ERROR);
        assertThat(logbackRow.getThrowableInfo().getClassName()).isEqualTo("java.lang.RuntimeException");
        assertThat(logbackRow.getThrowableInfo().getMessage()).isEqualTo("Test error message");
        assertThat(logbackRow.getTimeStamp()).isEqualTo(-2546575200L);
        assertThat(logbackRow.getMdc()).isEqualTo(emptyMap());
    }

    @Test
    void values_null_mapping() {
        ILoggingEvent event = mock(ILoggingEvent.class);
        when(event.getThreadName()).thenReturn(null);
        when(event.getLoggerName()).thenReturn(null);
        when(event.getMessage()).thenReturn(null);
        when(event.getLevel()).thenReturn(null);
        when(event.getThrowableProxy()).thenReturn(null);
        when(event.getTimeStamp()).thenReturn(0L);
        when(event.getMDCPropertyMap()).thenReturn(null);

        // call
        LogRow logRow = mapper.apply(event);

        LogbackRow logbackRow = (LogbackRow) logRow;
        assertThat(logbackRow.getThreadName()).isNull();
        assertThat(logbackRow.getLoggerName()).isNull();
        assertThat(logbackRow.getMessage()).isNull();
        assertThat(logbackRow.getLevel()).isNull();
        assertThat(logbackRow.getThrowableInfo()).isNull();
        assertThat(logbackRow.getTimeStamp()).isEqualTo(0L);
        assertThat(logbackRow.getMdc()).isNull();
    }
}