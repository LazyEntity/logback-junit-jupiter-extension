package samples;

import junit.jupiter.log.common.Level;
import junit.jupiter.log.common.LogExtension;
import junit.jupiter.log.logback.LogbackExtensionFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

class LogbackExtensionExample {
    @RegisterExtension
    static LogExtension logExtension = LogbackExtensionFactory.forAllLoggers();

    private static final Logger log = LoggerFactory.getLogger(LogbackExtensionExample.class);

    @Test
    void messageAssert() {
        log.info("Hello world");

        Assertions.assertThat(logExtension.getLogList())
                .anyMatch(logRow ->
                        logRow.getMessage().contains("Hello world") &&
                                Objects.equals(logRow.getLevel(), Level.INFO));
    }
}

