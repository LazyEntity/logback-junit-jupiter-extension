package junit.jupiter.log.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import junit.jupiter.log.common.LogRow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.function.Function;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ListLogbackAppenderImplTest {
    @InjectMocks
    private ListLogbackAppenderImpl appender;

    @Mock
    private Function<ILoggingEvent, LogRow> converterILoggingEventToLogRow;
    @Mock
    private List<LogRow> logRows;

    @Test
    void started_appender_append() {
        ILoggingEvent event = mock(ILoggingEvent.class);
        LogRow logRow = mock(LogRow.class);
        when(converterILoggingEventToLogRow.apply(eq(event)))
                .thenReturn(logRow);

        appender.start();
        // call
        appender.append(event);

        verify(logRows).add(eq(logRow));
    }

    @Test
    void not_started_appender_not_append() {
        ILoggingEvent event = mock(ILoggingEvent.class);

        // call
        appender.append(event);

        verify(converterILoggingEventToLogRow, times(0)).apply(any());
        verify(logRows, times(0)).add(any());
    }

    @Test
    void clear() {
        // call
        appender.clear();

        verify(logRows).clear();
    }

}
