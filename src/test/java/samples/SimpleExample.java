package samples;

import junit.jupiter.log.common.Level;
import junit.jupiter.log.common.LogExtension;
import junit.jupiter.log.common.LogRow;
import junit.jupiter.log.logback.LogbackExtensionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleExample {
    @RegisterExtension
    static LogExtension logExtension = LogbackExtensionFactory.forClass(Simple.class);

    @Test
    void simple_log_example() {
        new Simple().out();

        // call
        List<LogRow> logList = logExtension.getLogList();

        assertThat(logList).
                hasSize(1)
                .anyMatch(logRow ->
                        Objects.equals(logRow.getMessage(), "simple example") &&
                                Objects.equals(logRow.getLevel(), Level.ERROR) &&
                                Objects.equals(logRow.getLoggerName(), "samples.SimpleExample$Simple") &&
                                Objects.equals(logRow.getThreadName(), Thread.currentThread().getName()) &&
                                Objects.equals(logRow.getThrowableInfo().getMessage(), "Simple error") &&
                                Objects.equals(logRow.getThrowableInfo().getClassName(), "java.lang.RuntimeException") &&
                                Objects.equals(logRow.getMdcValue("simple"), "value") &&
                                getLocalDateTime(logRow).isBefore(LocalDateTime.now())
                );
    }

    private LocalDateTime getLocalDateTime(LogRow logRow) {
        return Instant.ofEpochMilli(logRow.getTimeStamp()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    static class Simple {
        private static final Logger log = LoggerFactory.getLogger(Simple.class);

        void out() {
            MDC.put("simple", "value");
            log.error("simple example", new RuntimeException("Simple error"));
            MDC.clear();
        }
    }
}
