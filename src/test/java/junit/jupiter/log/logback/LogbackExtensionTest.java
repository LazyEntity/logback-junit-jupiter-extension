package junit.jupiter.log.logback;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.Context;
import junit.jupiter.log.common.LogRow;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LogbackExtensionTest {
    @InjectMocks
    private LogbackExtension extension;

    @Mock
    private ListLogbackAppender appender;
    @Mock
    private Context loggerContext;
    @Spy
    private ArrayList<Logger> loggers;

    private final Logger logger1 = ((Logger) LoggerFactory.getLogger("1"));
    private final Logger logger2 = ((Logger) LoggerFactory.getLogger("2"));

    @BeforeEach
    void init() {
        loggers.add(logger1);
        loggers.add(logger2);
    }

    @AfterEach
    void after() {
        logger1.detachAppender(appender);
        logger2.detachAppender(appender);
    }

    @Test
    void beforeAll() {
        ExtensionContext context = mock(ExtensionContext.class);
        when(appender.getName()).thenReturn("Appender");

        // call
        extension.beforeAll(context);

        assertThat(logger1.getAppender("Appender")).isEqualTo(appender);
        assertThat(logger2.getAppender("Appender")).isEqualTo(appender);

        verify(appender).setContext(loggerContext);
        verify(appender).start();
    }

    @Test
    void afterAll() {
        ExtensionContext context = mock(ExtensionContext.class);
        logger1.addAppender(appender);
        logger2.addAppender(appender);

        // call
        extension.afterAll(context);

        assertThat(logger1.getAppender("Appender")).isNull();
        assertThat(logger2.getAppender("Appender")).isNull();

        verify(appender).stop();
    }

    @Test
    void afterEach() {
        ExtensionContext context = mock(ExtensionContext.class);

        // call
        extension.afterEach(context);

        verify(appender).clear();
    }

    @Test
    void getLogList() {
        LogRow logRow = mock(LogRow.class);
        List<LogRow> logRows = Collections.singletonList(logRow);
        when(appender.getLogRows()).thenReturn(logRows);

        // call
        List<LogRow> logList = extension.getLogList();

        assertThat(logList).hasSize(1)
                .first().isEqualTo(logRow);

    }
}
