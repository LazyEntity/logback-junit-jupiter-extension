package junit.jupiter.log.logback;

import junit.jupiter.log.common.LogThrowableInfo;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LogbackThrowableInfoTest {
    @Test
    void equals_check() {
        EqualsVerifier.forClass(LogbackThrowableInfo.class).verify();
    }

    @Test
    void logThrowableInfo_get_methods() {
        LogThrowableInfo logThrowableInfo = LogbackThrowableInfo.builder()
                .message("Message")
                .className("java.lang.RuntimeException")
                .build();

        assertThat(logThrowableInfo.getMessage()).isEqualTo("Message");
        assertThat(logThrowableInfo.getClassName()).isEqualTo("java.lang.RuntimeException");
    }
}