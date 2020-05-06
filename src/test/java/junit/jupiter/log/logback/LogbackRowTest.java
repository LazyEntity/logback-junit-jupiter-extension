package junit.jupiter.log.logback;

import junit.jupiter.log.common.Level;
import junit.jupiter.log.common.LogRow;
import junit.jupiter.log.common.LogThrowableInfo;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LogbackRowTest {
    @Test
    void equals_check() {
        EqualsVerifier.forClass(LogbackRow.class).verify();
    }

    @Test
    void logRow_get_methods() {
        LogThrowableInfo logThrowableInfo = mock(LogThrowableInfo.class);
        LogRow logRow = LogbackRow.builder()
                .threadName("main-thread")
                .loggerName("TestLogger")
                .message("Test Message")
                .level(Level.ERROR)
                .throwableInfo(logThrowableInfo)
                .timeStamp(-2546575200L)
                .build();

        assertThat(logRow.getThreadName()).isEqualTo("main-thread");
        assertThat(logRow.getLoggerName()).isEqualTo("TestLogger");
        assertThat(logRow.getMessage()).isEqualTo("Test Message");
        assertThat(logRow.getLevel()).isEqualTo(Level.ERROR);
        assertThat(logRow.getThrowableInfo()).isEqualTo(logThrowableInfo);
        assertThat(logRow.getTimeStamp()).isEqualTo(-2546575200L);
    }

    @SuppressWarnings("unchecked")
    @Test
    void getMdcValue() {
        Map<String, String> mdcMap = mock(Map.class);
        when(mdcMap.get("test")).thenReturn("value");

        LogRow logRow = LogbackRow.builder()
                .mdc(mdcMap)
                .build();

        assertThat(logRow.getMdcValue("test")).isEqualTo("value");
    }
}