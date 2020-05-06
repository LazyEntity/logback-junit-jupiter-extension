package junit.jupiter.log.logback;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.Context;
import junit.jupiter.log.common.LogExtension;
import junit.jupiter.log.common.LogRow;
import lombok.Builder;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Collections;
import java.util.List;

/**
 * Extension that allows to testing logging
 */
@Builder
public final class LogbackExtension implements LogExtension, BeforeAllCallback, AfterAllCallback, AfterEachCallback {
    private final ListLogbackAppender appender;
    private final Context loggerContext;
    private final Iterable<Logger> loggers;

    @Override
    public void beforeAll(ExtensionContext context) {
        loggers.forEach(logger -> logger.addAppender(appender));
        appender.setContext(loggerContext);
        appender.start();
    }

    @Override
    public void afterAll(ExtensionContext context) {
        loggers.forEach(logger -> logger.detachAppender(appender));
        appender.stop();
    }

    @Override
    public void afterEach(ExtensionContext context) {
        appender.clear();
    }

    @Override
    public List<LogRow> getLogList() {
        return Collections.unmodifiableList(appender.getLogRows());
    }
}
