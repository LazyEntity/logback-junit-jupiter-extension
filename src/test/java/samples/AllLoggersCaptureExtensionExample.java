package samples;

import junit.jupiter.log.common.LogExtension;
import junit.jupiter.log.logback.LogbackExtensionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;


class AllLoggersCaptureExtensionExample {
    @RegisterExtension
    static LogExtension logExtension = LogbackExtensionFactory.forAllLoggers();

    @Test
    void all_loggers_capture() {
        Logger testLogger = LoggerFactory.getLogger("TestLogger");
        testLogger.info("test");

        new A().log();
        new B().log();

        assertThat(logExtension.getLogList())
                .anyMatch(logRow -> "TestLogger".equals(logRow.getLoggerName()))
                .anyMatch(logRow -> "samples.AllLoggersCaptureExtensionExample$B".equals(logRow.getLoggerName()))
                .anyMatch(logRow -> "samples.AllLoggersCaptureExtensionExample$A".equals(logRow.getLoggerName()));
    }

    static class A {
        private static final Logger log = LoggerFactory.getLogger(A.class);

        void log() {
            log.info("A class log");
        }
    }

    static class B {
        private static final Logger log = LoggerFactory.getLogger(B.class);

        void log() {
            log.info("B class log");
        }
    }
}

