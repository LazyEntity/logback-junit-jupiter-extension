package junit.jupiter.log.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import junit.jupiter.log.common.LogRow;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Function;

/**
 * Handle logger messages and store in {@link java.util.ArrayList}
 */
@RequiredArgsConstructor
public final class ListLogbackAppenderImpl extends UnsynchronizedAppenderBase<ILoggingEvent> implements ListLogbackAppender {
    private final Function<ILoggingEvent, LogRow> logbackEventToLogbackRowMapper;
    private final List<LogRow> logRows;

    @Override
    public void append(ILoggingEvent e) {
        if (isStarted()) {
            LogRow logRow = logbackEventToLogbackRowMapper.apply(e);
            logRows.add(logRow);
        }
    }

    @Override
    public List<LogRow> getLogRows() {
        return logRows;
    }

    @Override
    public void clear() {
        logRows.clear();
    }
}
