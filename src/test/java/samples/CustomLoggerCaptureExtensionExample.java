package samples;

import junit.jupiter.log.common.LogExtension;
import junit.jupiter.log.logback.LogbackExtensionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomLoggerCaptureExtensionExample {
    @RegisterExtension
    static LogExtension logExtension = LogbackExtensionFactory.forLogger("myLogger");

    @Test
    void custom_logger_log() {
        Logger myLogger = LoggerFactory.getLogger("myLogger");
        Logger notMyLogger = LoggerFactory.getLogger("notMyLogger");

        myLogger.info("My log");
        notMyLogger.info("Not My log");

        assertThat(logExtension.getLogList())
                .hasSize(1)
                .anyMatch(logRow -> "myLogger".equals(logRow.getLoggerName()));
    }
}
